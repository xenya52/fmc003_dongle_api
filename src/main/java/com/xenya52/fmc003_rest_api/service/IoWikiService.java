package com.xenya52.fmc003_rest_api.service;

import com.xenya52.fmc003_rest_api.entity.dto.GetResponseDto;
import com.xenya52.fmc003_rest_api.entity.model.IoWikiModel;
import com.xenya52.fmc003_rest_api.repository.IoWikiRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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
        return wikiModel == null ? null : new GetResponseDto(wikiModel);
    }

    public GetResponseDto getIoWikiByName(String name) {
        IoWikiModel wikiModel = ioWikiRepository
            .findByWikiName(name)
            .orElse(null);
        return wikiModel == null ? null : new GetResponseDto(wikiModel);
    }

    public List<GetResponseDto> getIoWikiList() {
        List<IoWikiModel> ioWikiModelList = ioWikiRepository.findAll();
        List<GetResponseDto> ioWikiDtoList = new ArrayList<>();

        for (IoWikiModel ioWikiModel : ioWikiModelList) {
            ioWikiDtoList.add(new GetResponseDto(ioWikiModel));
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
}
