package com.xenya52.fmc003_rest_api.repository;

import com.xenya52.fmc003_rest_api.model.IoWikiModel;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IoWikiRepository extends MongoRepository<IoWikiModel, String> {
    Optional<IoWikiModel> findByWikiId(String id);
    Optional<IoWikiModel> findByWikiName(String name);
    List<IoWikiModel> findAll();
}
