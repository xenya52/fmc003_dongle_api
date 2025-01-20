package com.xenya52.fmc003_rest_api.service;

import com.xenya52.fmc003_rest_api.entity.model.IoWikiModel;
import com.xenya52.fmc003_rest_api.repository.IoWikiRepository;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
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
    public IoWikiModel getIoWikiById(String id) {
        return ioWikiRepository.findByWikiId(id).orElse(null);
    }

    public IoWikiModel getIoWikiByName(String name) {
        return ioWikiRepository.findByWikiName(name).orElse(null);
    }

    public List<IoWikiModel> getIoWikiMap() {
        try {
            return ioWikiRepository.findAll();
        } catch (Exception e) {
            // Todo
            log.error("Error retrieving IoWiki map", e);
            return null;
        }
    }

    public boolean saveIoWiki(IoWikiModel ioWikiModel) {
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

    public boolean saveIoWikiList(List<IoWikiModel> ioWikiList) {
        for (IoWikiModel ioWikiModel : ioWikiList) {
            if (!saveIoWiki(ioWikiModel)) {
                return false;
            }
        }
        return true;
    }
}
