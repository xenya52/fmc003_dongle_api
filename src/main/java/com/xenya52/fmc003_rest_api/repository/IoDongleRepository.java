package com.xenya52.fmc003_rest_api.repository;

import com.xenya52.fmc003_rest_api.entity.model.teltonika.dongle.TeltonikaFmc003DongleModel;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository interface for IoDongleModel entities.
 * Extends the MongoRepository interface to provide CRUD operations.
 */
public interface IoDongleRepository
    extends MongoRepository<TeltonikaFmc003DongleModel, String> {
    /**
     * Finds an IoDongleModel by its device ID.
     *
     * @param id the device ID to search for
     * @return an Optional containing the found IoDongleModel, or empty if not found
     */
    Optional<TeltonikaFmc003DongleModel> findById(String id);

    /**
     * Retrieves all IoDongleModel entities.
     *
     * @return a list of all IoDongleModel entities
     */
    List<TeltonikaFmc003DongleModel> findAll();
}
