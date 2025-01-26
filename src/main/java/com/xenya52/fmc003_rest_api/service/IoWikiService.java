package com.xenya52.fmc003_rest_api.service;

import com.xenya52.fmc003_rest_api.entity.dto.GetResponseDto;
import com.xenya52.fmc003_rest_api.entity.model.IoWikiModel;
import com.xenya52.fmc003_rest_api.repository.IoWikiRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class IoWikiService {

    // Attributes
    @Autowired
    private IoWikiRepository ioWikiRepository;

    // Constructors
    public IoWikiService() {}

    // Methods
    public GetResponseDto getIoWikiById(String id) {
        IoWikiModel wikiModel = ioWikiRepository.findByWikiId(id).orElse(null);
        Map<String, String> links = Map.of(
            "prev",
            getPrevId(id),
            "next",
            getNextId(id)
        );
        return wikiModel == null ? null : new GetResponseDto(wikiModel, links);
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

        for (IoWikiModel ioWikiModel : ioWikiModelList) {
            Map<String, String> links = Map.of(
                "prev",
                getNextId(ioWikiModel.getWikiId()),
                "next",
                getNextId(ioWikiModel.getWikiId())
            );
            ioWikiDtoList.add(new GetResponseDto(ioWikiModel, links));
        }
        return ioWikiDtoList;
    }

    public boolean saveIoWikiList(List<IoWikiModel> ioWikiList) {
        for (IoWikiModel ioWikiModel : ioWikiList) {
            if (!saveIoWiki(ioWikiModel)) {
                return false;
            }
        }
        return true;
    }

    // Todo sort the list by id
    private boolean saveIoWiki(IoWikiModel ioWikiModel) {
        try {
            ioWikiRepository.save(ioWikiModel);
        } catch (
            IllegalArgumentException | OptimisticLockingFailureException e
        ) {
            // Todo make it more specific
            // https://medium.com/@aedemirsen/spring-boot-global-exception-handler-842d7143cf2a
            return false;
        }
        return true;
    }

    private String getPrevId(String id) {
        List<IoWikiModel> ioWikiModelList = ioWikiRepository.findAll();
        ioWikiModelList.sort(Comparator.comparing(IoWikiModel::getWikiId));
        int index = -1;

        // Debugging
        for (int i = 0; i < ioWikiModelList.size(); i++) {
            if (ioWikiModelList.get(i).getWikiId().equals(id)) {
                index = i;
                break;
            }
        }

        if (index > 0) {
            return ioWikiModelList.get(index - 1).getWikiId();
        } else {
            return "";
        }
    }

    private String getNextId(String id) {
        List<IoWikiModel> ioWikiModelList = ioWikiRepository.findAll();
        ioWikiModelList.sort(Comparator.comparing(IoWikiModel::getWikiId));

        // Debugging
        for (IoWikiModel ioWikiModel : ioWikiModelList) {
            System.out.println("Wiki ID: " + ioWikiModel.getWikiId());
        }

        int index = -1;
        for (int i = 0; i < ioWikiModelList.size(); i++) {
            if (ioWikiModelList.get(i).getWikiId().equals(id)) {
                index = i;
                break;
            }
        }

        if (index >= 0 && index < ioWikiModelList.size() - 1) {
            return ioWikiModelList.get(index + 1).getWikiId();
        } else {
            return "";
        }
    }
}
