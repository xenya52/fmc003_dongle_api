package com.xenya52.fmc003_rest_api.service;

import com.xenya52.fmc003_rest_api.model.IoWikiModel;
import com.xenya52.fmc003_rest_api.repository.TeltonikaRepository;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class IoWikiService {

    // Attributes
    @Autowired
    private TeltonikaRepository teltonikaRepository;

    // Constructors
    public IoWikiService() {}

    // Methods
    public IoWikiModel getIoWikiById(String id) {
        log.info("Fetching IoWikiModel with id: " + id);
        return teltonikaRepository.findById(id).orElse(null);
    }

    public IoWikiModel getIoWikiByName(String name) {
        log.info("Fetching IoWikiModel with name: " + name);
        return teltonikaRepository.findByName(name).orElse(null);
    }

    public Map<String, String> getIoWikiMap() {
        log.info("Fetching all IoWikiModels");
        Map<String, String> ioWikiMap = new HashMap<>();
        List<IoWikiModel> ioWikiModels = teltonikaRepository.findAll();
        for (IoWikiModel ioWikiModel : ioWikiModels) {
            ioWikiMap.put(ioWikiModel.getId(), ioWikiModel.getName());
        }
        return ioWikiMap;
    }

    public boolean saveIoWiki(IoWikiModel ioWikiModel) {
        try {
            teltonikaRepository.save(ioWikiModel);
        } catch (
            IllegalArgumentException | OptimisticLockingFailureException e
        ) {
            // Todo make it more specific
            // https://medium.com/@aedemirsen/spring-boot-global-exception-handler-842d7143cf2a
            return false;
        }
        return true;
    }

    public boolean saveIoWikiMap(Map<String, String> ioWikiDict) {
        for (Map.Entry<String, String> entry : ioWikiDict.entrySet()) {
            IoWikiModel ioWikiModel = new IoWikiModel(
                entry.getKey(),
                entry.getValue()
            );
            if (!saveIoWiki(ioWikiModel)) {
                return false;
            }
        }
        return true;
    }
}
