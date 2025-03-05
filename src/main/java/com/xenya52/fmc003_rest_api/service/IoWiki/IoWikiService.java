package com.xenya52.fmc003_rest_api.service.IoWiki;

import com.xenya52.fmc003_rest_api.entity.dto.GetResponseDto;
import com.xenya52.fmc003_rest_api.entity.model.IoWikiModel;
import com.xenya52.fmc003_rest_api.repository.IoWikiRepository;
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
public class IoWikiService {

    private static final Logger LOGGER = Logger.getLogger(
        IoWikiService.class.getName()
    );

    // Attributes
    @Autowired
    private IoWikiRepository ioWikiRepository;

    // Methods
    public List<GetResponseDto> getIoWikiListById(List<String> ids) {
        List<GetResponseDto> getResponseDtos = new ArrayList<>();

        for (String id : ids) {
            GetResponseDto getResponseDto = getIoWikiById(id);

            if (getResponseDto == null) {
                // TODO
            } else {
                getResponseDtos.add(getResponseDto);
            }
        }
        return getResponseDtos;
    }

    private GetResponseDto getIoWikiById(String id) {
        IoWikiModel wikiModel = ioWikiRepository.findByWikiId(id).orElse(null);
        try {
            Map<String, String> links = Map.of(
                "prev",
                getPrevId(id),
                "next",
                getNextId(id)
            );
            return wikiModel == null
                ? null
                : new GetResponseDto(wikiModel, links);
        } catch (IllegalArgumentException iae) {
            LOGGER.log(
                Level.SEVERE,
                "IllegalArgumentException: The id is not valid. Please provide a valid id: {0}",
                iae.getMessage()
            );
            throw new IllegalArgumentException(
                "The id is not valid. Please provide a valid id.",
                iae
            );
        } catch (NullPointerException npe) {
            LOGGER.log(
                Level.SEVERE,
                "NullPointerException: The id is not valid. Please provide a valid id: {0}",
                npe.getMessage()
            );
            throw new IllegalArgumentException(
                "The id is not valid. Please provide a valid id.",
                npe
            );
        }
    }

    public GetResponseDto getIoWikiByName(String name) {
        IoWikiModel wikiModel = ioWikiRepository
            .findByWikiName(name)
            .orElse(null);
        Map<String, String> links = Map.of(
            "prev",
            getPrevId(wikiModel.getWikiId()),
            "next",
            getNextId(wikiModel.getWikiId())
        );
        return wikiModel == null ? null : new GetResponseDto(wikiModel, links);
    }

    public List<GetResponseDto> getIoWikiList() {
        List<IoWikiModel> ioWikiModelList = ioWikiRepository.findAll();
        List<GetResponseDto> ioWikiDtoList = new ArrayList<>();
        try {
            for (IoWikiModel ioWikiModel : ioWikiModelList) {
                // Next and Previous id are unnecessary if I get the whole list. Furthermore it saves time
                // Todo: Idk... is "null" bad practice? In this case?
                ioWikiDtoList.add(new GetResponseDto(ioWikiModel, null));
            }
        } catch (IllegalArgumentException iae) {
            LOGGER.log(
                Level.SEVERE,
                "IllegalArgumentException: The id is not valid. Please provide a valid id: {0}",
                iae.getMessage()
            );
            throw new IllegalArgumentException(
                "The id is not valid. Please provide a valid id.",
                iae
            );
        } catch (NullPointerException npe) {
            LOGGER.log(
                Level.SEVERE,
                "NullPointerException: The id is not valid. Please provide a valid id: {0}",
                npe.getMessage()
            );
            throw new IllegalArgumentException(
                "The id is not valid. Please provide a valid id.",
                npe
            );
        }
        return ioWikiDtoList;
    }

    public boolean saveIoWikiList(List<IoWikiModel> ioWikiList) {
        try {
            for (IoWikiModel ioWikiModel : ioWikiList) {
                if (!saveIoWiki(ioWikiModel)) {
                    return false;
                }
            }
        } catch (IllegalArgumentException iae) {
            LOGGER.log(
                Level.SEVERE,
                "IllegalArgumentException: The id is not valid. Please provide a valid id: {0}",
                iae.getMessage()
            );
            throw new IllegalArgumentException(
                "The id is not valid. Please provide a valid id.",
                iae
            );
        } catch (NullPointerException npe) {
            LOGGER.log(
                Level.SEVERE,
                "NullPointerException: The id is not valid. Please provide a valid id: {0}",
                npe.getMessage()
            );
            throw new IllegalArgumentException(
                "The id is not valid. Please provide a valid id.",
                npe
            );
        }
        return true;
    }

    // Todo sort the list by id
    public boolean saveIoWiki(IoWikiModel ioWikiModel) {
        try {
            ioWikiRepository.save(ioWikiModel);
        } catch (IllegalArgumentException iae) {
            LOGGER.log(
                Level.SEVERE,
                "IllegalArgumentException: The given IoDongleModel is not valid: {0}",
                iae.getMessage()
            );
            throw new IllegalArgumentException(
                "The given IoDongleModel is not valid.",
                iae
            );
        } catch (OptimisticLockingFailureException olfe) {
            LOGGER.log(
                Level.SEVERE,
                "OptimisticLockingFailureException: The given IoDongleModel has already changed his state in the database: {0}",
                olfe.getMessage()
            );
            throw new OptimisticLockingFailureException(
                "The given IoDongleModel has already changed his state in the database.",
                olfe
            );
        }
        return true;
    }

    public boolean updateIoWiki(IoWikiModel ioWikiModel) {
        try {
            if (ioWikiRepository.existsById(ioWikiModel.getWikiId())) {
                ioWikiRepository.save(ioWikiModel);
                return true;
            } else {
                LOGGER.log(
                    Level.WARNING,
                    "Update failed: IoWikiModel with id {0} does not exist.",
                    ioWikiModel.getWikiId()
                );
                return false;
            }
        } catch (IllegalArgumentException iae) {
            LOGGER.log(
                Level.SEVERE,
                "IllegalArgumentException: The given IoWikiModel is not valid: {0}",
                iae.getMessage()
            );
            throw new IllegalArgumentException(
                "The given IoWikiModel is not valid.",
                iae
            );
        } catch (OptimisticLockingFailureException olfe) {
            LOGGER.log(
                Level.SEVERE,
                "OptimisticLockingFailureException: The given IoWikiModel has already changed its state in the database: {0}",
                olfe.getMessage()
            );
            throw new OptimisticLockingFailureException(
                "The given IoWikiModel has already changed its state in the database.",
                olfe
            );
        }
    }

    public boolean deleteIoWiki(String id) {
        try {
            if (ioWikiRepository.existsById(id)) {
                ioWikiRepository.deleteById(id);
                return true;
            } else {
                LOGGER.log(
                    Level.WARNING,
                    "Delete failed: IoWikiModel with id {0} does not exist.",
                    id
                );
                return false;
            }
        } catch (IllegalArgumentException iae) {
            LOGGER.log(
                Level.SEVERE,
                "IllegalArgumentException: The given IoWikiModel is not valid: {0}",
                iae.getMessage()
            );
            throw new IllegalArgumentException(
                "The given IoWikiModel is not valid.",
                iae
            );
        } catch (OptimisticLockingFailureException olfe) {
            LOGGER.log(
                Level.SEVERE,
                "OptimisticLockingFailureException: The given IoWikiModel has already changed its state in the database: {0}",
                olfe.getMessage()
            );
            throw new OptimisticLockingFailureException(
                "The given IoWikiModel has already changed its state in the database.",
                olfe
            );
        }
    }

    private String getPrevId(String id) {
        List<IoWikiModel> ioWikiModelList = ioWikiRepository.findAll();
        try {
            ioWikiModelList.sort(
                Comparator.comparing(IoWikiModel::getWikiIdAsInt)
            );
            int index = ioWikiModelList.indexOf(
                ioWikiModelList
                    .stream()
                    .filter(wikiModel -> wikiModel.getWikiId().equals(id))
                    .findFirst()
                    .orElse(null)
            );

            if (index > 0) {
                return ioWikiModelList.get(index - 1).getWikiId();
            } else {
                return "";
            }
        } catch (NullPointerException npe) {
            LOGGER.log(
                Level.SEVERE,
                "NullPointerException: The id is not valid. Please provide a valid id: {0}",
                npe.getMessage()
            );
            throw new IllegalArgumentException(
                "The id is not valid. Please provide a valid id.",
                npe
            );
        }
    }

    private String getNextId(String id) {
        List<IoWikiModel> ioWikiModelList = ioWikiRepository.findAll();
        try {
            ioWikiModelList.sort(
                Comparator.comparing(IoWikiModel::getWikiIdAsInt)
            );
            int index = ioWikiModelList.indexOf(
                ioWikiModelList
                    .stream()
                    .filter(wikiModel -> wikiModel.getWikiId().equals(id))
                    .findFirst()
                    .orElse(null)
            );

            if (index >= 0 && index < ioWikiModelList.size() - 1) {
                return ioWikiModelList.get(index + 1).getWikiId();
            } else {
                return "";
            }
        } catch (NullPointerException npe) {
            LOGGER.log(
                Level.SEVERE,
                "NullPointerException: The id is not valid. Please provide a valid id: {0}",
                npe.getMessage()
            );
            throw new IllegalArgumentException(
                "The id is not valid. Please provide a valid id.",
                npe
            );
        }
    }
}
