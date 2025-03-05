package com.xenya52.fmc003_rest_api.service.IoDongle;

import com.xenya52.fmc003_rest_api.entity.dto.GetResponseDto;
import com.xenya52.fmc003_rest_api.entity.model.IoDongleModel;
import com.xenya52.fmc003_rest_api.repository.IoDongleRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Component;

@Component
public class IoDongleService {

    private static final Logger LOGGER = Logger.getLogger(
        IoDongleService.class.getName()
    );

    // Attributes
    @Autowired
    private IoDongleRepository ioDongleRepository;

    // Constructors
    public IoDongleService() {}

    // Methods

    // Todo implement edge cases for saveIoDongleList
    public boolean saveIoDongleList(List<IoDongleModel> ioDongleModels) {
        for (IoDongleModel ioDongleModel : ioDongleModels) {
            saveIoDongle(ioDongleModel);
        }
        return true;
    }

    public List<GetResponseDto> getIoDongleList() {
        List<IoDongleModel> ioDongleModelList = ioDongleRepository.findAll();
        List<GetResponseDto> ioDongleDtoList = new ArrayList<>();

        for (IoDongleModel ioDongleModel : ioDongleModelList) {
            // Next and Previous id are unnecessary if I get the whole list. Furthermore it saves time
            ioDongleDtoList.add(new GetResponseDto(ioDongleModel, null));
        }
        return ioDongleDtoList;
    }

    public List<GetResponseDto> getIoDongleListBySpecificId(List<String> ids) {
        List<GetResponseDto> responseDtos = new ArrayList<>();

        for (String id : ids) {
            GetResponseDto getResponseDto = getIoDongleById(id);

            if (getResponseDto == null) {
                LOGGER.log(Level.WARNING, "IoDongle with id {0} not found", id);
            } else {
                responseDtos.add(getResponseDto);
            }
        }
        return responseDtos;
    }

    private GetResponseDto getIoDongleById(String id) {
        try {
            IoDongleModel dongleModel = ioDongleRepository
                .findByDeviceId(id)
                .orElse(null);
            Map<String, String> links = Map.of(
                "prev",
                getPrevId(id),
                "next",
                getNextId(id)
            );
            return dongleModel == null
                ? null
                : new GetResponseDto(dongleModel, links);
        } catch (IllegalArgumentException iae) {
            LOGGER.log(
                Level.SEVERE,
                "Invalid id provided: {0}",
                iae.getMessage()
            );
            throw new IllegalArgumentException(
                "The id is not valid. Please provide a valid id.",
                iae
            );
        } catch (NullPointerException npe) {
            LOGGER.log(
                Level.SEVERE,
                "NullPointerException for id: {0}",
                npe.getMessage()
            );
            throw new IllegalArgumentException(
                "The id is not valid. Please provide a valid id.",
                npe
            );
        }
    }

    public IoDongleModel saveIoDongle(IoDongleModel ioDongleModel) {
        try {
            return ioDongleRepository.save(ioDongleModel);
        } catch (IllegalArgumentException iae) {
            LOGGER.log(
                Level.SEVERE,
                "Invalid IoDongleModel provided: {0}",
                iae.getMessage()
            );
            throw new IllegalArgumentException(
                "The given IoDongleModel is not valid.",
                iae
            );
        } catch (OptimisticLockingFailureException olfe) {
            LOGGER.log(
                Level.SEVERE,
                "Optimistic locking failure for IoDongleModel: {0}",
                olfe.getMessage()
            );
            throw new OptimisticLockingFailureException(
                "The given IoDongleModel has already changed its state in the database.",
                olfe
            );
        }
    }

    public IoDongleModel updateIoDongle(IoDongleModel ioDongleModel) {
        if (!ioDongleRepository.existsById(ioDongleModel.getDeviceId())) {
            return null;
        }
        return saveIoDongle(ioDongleModel);
    }

    public boolean deleteIoDongle(String id) {
        if (!ioDongleRepository.existsById(id)) {
            return false;
        }
        ioDongleRepository.deleteById(id);
        return true;
    }

    private String getPrevId(String id) {
        List<IoDongleModel> ioDongleModelList = ioDongleRepository.findAll();
        try {
            ioDongleModelList.sort(
                Comparator.comparing(IoDongleModel::getDeviceId)
            );
            int index = 0;
            for (int i = 0; i < ioDongleModelList.size(); i++) {
                if (ioDongleModelList.get(i).getDeviceId().equals(id)) {
                    index = i;
                    break;
                }
            }
            if (index == 0) {
                return ioDongleModelList
                    .get(ioDongleModelList.size() - 1)
                    .getDeviceId();
            } else {
                return ioDongleModelList.get(index - 1).getDeviceId();
            }
        } catch (NullPointerException npe) {
            LOGGER.log(
                Level.SEVERE,
                "NullPointerException while getting previous id for: {0}",
                npe.getMessage()
            );
            throw new IllegalArgumentException(
                "The id is not valid. Please provide a valid id.",
                npe
            );
        }
    }

    private String getNextId(String id) {
        List<IoDongleModel> ioDongleModelList = ioDongleRepository.findAll();
        try {
            ioDongleModelList.sort(
                Comparator.comparing(IoDongleModel::getDeviceId)
            );
            int index = 0;
            for (int i = 0; i < ioDongleModelList.size(); i++) {
                if (ioDongleModelList.get(i).getDeviceId().equals(id)) {
                    index = i;
                    break;
                }
            }
            if (index == ioDongleModelList.size() - 1) {
                return ioDongleModelList.get(0).getDeviceId();
            } else {
                return ioDongleModelList.get(index + 1).getDeviceId();
            }
        } catch (NullPointerException npe) {
            LOGGER.log(
                Level.SEVERE,
                "NullPointerException while getting next id for: {0}",
                npe.getMessage()
            );
            throw new IllegalArgumentException(
                "The id is not valid. Please provide a valid id.",
                npe
            );
        }
    }
}
