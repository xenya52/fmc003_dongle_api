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
    public IoDongleService() {
        this.ioDongleRepository = new IoDongleRepository();
    }

    // Methods
    public GetResponseDto getIoDongleById(String id) {
        IoDongleModel dongleModel = ioDongleRepository
            .findByDongleId(id)
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
            .findByDongleName(name)
            .orElse(null);
        Map<String, String> links = Map.of(
            "prev",
            getPrevId(dongleModel.getDongleId()),
            "next",
            getNextId(dongleModel.getDongleId())
        );
        return dongleModel == null
            ? null
            : new GetResponseDto(dongleModel, links);
    }

    public List<GetResponseDto> getIoDongleList() {
        List<IoDongleModel> ioDongleModelList = ioDongleRepository.findAll();
        List<GetResponseDto> ioDongleDtoList = new ArrayList<>();

        for (IoDongleModel ioDongleModel : ioDongleModelList) {
            Map<String, String> links = Map.of(
                "prev",
                getNextId(ioDongleModel.getDongleId()),
                "next",
                getNextId(ioDongleModel.getDongleId())
            );
            ioDongleDtoList.add(new GetResponseDto(ioDongleModel, links));
        }
        return ioDongleDtoList;
    }

    private String getPrevId(String id) {
        List<IoDongleModel> ioDongleModelList = ioDongleRepository.findAll();
        ioDongleModelList.sort(
            Comparator.comparing(IoDongleModel::getDongleId)
        );
        int index = 0;
        for (int i = 0; i < ioDongleModelList.size(); i++) {
            if (ioDongleModelList.get(i).getDongleId().equals(id)) {
                index = i;
                break;
            }
        }
        if (index == 0) {
            return ioDongleModelList
                .get(ioDongleModelList.size() - 1)
                .getDongleId();
        } else {
            return ioDongleModelList.get(index - 1).getDongleId();
        }
    }

    private String getNextId(String id) {
        List<IoDongleModel> ioDongleModelList = ioDongleRepository.findAll();
        ioDongleModelList.sort(
            Comparator.comparing(IoDongleModel::getDongleId)
        );
        int index = 0;
        for (int i = 0; i < ioDongleModelList.size(); i++) {
            if (ioDongleModelList.get(i).getDongleId().equals(id)) {
                index = i;
                break;
            }
        }
        if (index == ioDongleModelList.size() - 1) {
            return ioDongleModelList.get(0).getDongleId();
        } else {
            return ioDongleModelList.get(index + 1).getDongleId();
        }
    }
}
