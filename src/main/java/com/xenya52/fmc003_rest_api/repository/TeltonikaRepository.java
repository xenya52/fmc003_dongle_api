package com.xenya52.fmc003_rest_api.repository;

import com.xenya52.fmc003_rest_api.model.IoWikiModel;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TeltonikaRepository
    extends MongoRepository<IoWikiModel, String> {
    Optional<IoWikiModel> findById(String id);
    Optional<IoWikiModel> findByName(String name);
    List<IoWikiModel> findAll();
}
