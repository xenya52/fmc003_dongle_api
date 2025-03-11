package com.xenya52.fmc003_rest_api.repository;

import com.xenya52.fmc003_rest_api.entity.model.IoWikiModel;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository interface for IoWikiModel entity.
 * Provides methods to perform CRUD operations on IoWikiModel.
 */
public interface IoWikiRepository extends MongoRepository<IoWikiModel, String> {
    /**
     * Finds an IoWikiModel by its wiki ID.
     *
     * @param id the wiki ID of the IoWikiModel
     * @return an Optional containing the found IoWikiModel, or an empty Optional if no IoWikiModel is found
     */
    Optional<IoWikiModel> findByWikiId(String id);

    /**
     * Finds an IoWikiModel by its wiki name.
     *
     * @param name the wiki name of the IoWikiModel
     * @return an Optional containing the found IoWikiModel, or an empty Optional if no IoWikiModel is found
     */
    Optional<IoWikiModel> findByWikiName(String name);

    /**
     * Retrieves all IoWikiModel entities.
     *
     * @return a list of all IoWikiModel entities
     */
    List<IoWikiModel> findAll();
}
