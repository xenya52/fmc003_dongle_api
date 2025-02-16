package com.xenya52.fmc003_rest_api.service;

import com.xenya52.fmc003_rest_api.entity.dto.GetResponseDto;
import com.xenya52.fmc003_rest_api.entity.model.IoDongleModel;
import com.xenya52.fmc003_rest_api.entity.model.IoWikiModel;
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
        } catch (Exception e) {
            // Todo implement logging and exception handling
            return null;
        }
    }

    // Todo implement edge cases for saveIoDongle
    public void saveIoDongle(IoDongleModel ioDongleModel) {
        try {
            ioDongleRepository.save(ioDongleModel);
        } catch (Exception e) {
            // TODO fix this
            // Debug
            System.out.println(e.toString());
            System.out.println("Run into saveIoDongle exception");
        }
    }

    public List<GetResponseDto> getIoDongleList() {
        List<IoDongleModel> ioDongleModelList = ioDongleRepository.findAll();
        List<GetResponseDto> ioDongleDtoList = new ArrayList<>();

        for (IoDongleModel ioDongleModel : ioDongleModelList) {
            // Next and Previous id are unnecessary if I get the whole list. Furthermore it saves time
            // Todo: Idk... is "null" bad practice? In this case?
            ioDongleDtoList.add(new GetResponseDto(ioDongleModel, null));
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
