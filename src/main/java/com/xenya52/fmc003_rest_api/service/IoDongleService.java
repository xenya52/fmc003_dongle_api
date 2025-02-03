package com.xenya52.fmc003_rest_api.service;

import com.xenya52.fmc003_rest_api.entity.dto.GetResponseDto;
import com.xenya52.fmc003_rest_api.entity.model.IoDongleModel;
import com.xenya52.fmc003_rest_api.repository.IoDongleRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class IoDongleService {

    // Attributes
    @Autowired
    private IoDongleRepository ioDongleRepository;

    // Constructors
    public IoDongleService() {}

    // Methods
    public GetResponseDto getIoDongleById(String id) {
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
    }

    public GetResponseDto getIoDongleByName(String name) {
        IoDongleModel dongleModel = ioDongleRepository
            .findBySasPolicyName(name)
            .orElse(null);
        Map<String, String> links = Map.of(
            "prev",
            getPrevId(dongleModel.getDeviceId()),
            "next",
            getNextId(dongleModel.getDeviceId())
        );
        return dongleModel == null
            ? null
            : new GetResponseDto(dongleModel, links);
    }

    public void saveIoDongle(IoDongleModel ioDongleModel) {
        try {
            ioDongleRepository.save(ioDongleModel);
        } catch (Exception e) {
            log.error("Error saving IoDongleModel: {}", e.getMessage());
        }
    }

    public List<GetResponseDto> getIoDongleList() {
        List<IoDongleModel> ioDongleModelList = ioDongleRepository.findAll();
        List<GetResponseDto> ioDongleDtoList = new ArrayList<>();

        for (IoDongleModel ioDongleModel : ioDongleModelList) {
            Map<String, String> links = Map.of(
                "prev",
                getNextId(ioDongleModel.getDeviceId()),
                "next",
                getNextId(ioDongleModel.getDeviceId())
            );
            ioDongleDtoList.add(new GetResponseDto(ioDongleModel, links));
        }
        return ioDongleDtoList;
    }

    private String getPrevId(String id) {
        List<IoDongleModel> ioDongleModelList = ioDongleRepository.findAll();
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
    }

    private String getNextId(String id) {
        List<IoDongleModel> ioDongleModelList = ioDongleRepository.findAll();
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
    }
}
