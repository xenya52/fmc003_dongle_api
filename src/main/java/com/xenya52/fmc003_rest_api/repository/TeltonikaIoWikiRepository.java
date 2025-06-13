package com.xenya52.fmc003_rest_api.repository;

import com.xenya52.fmc003_rest_api.entity.model.teltonika.TeltonikaIoWikiModel;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository interface for IoWikiModel entity.
 * Provides methods to perform CRUD operations on IoWikiModel.
 */
public interface TeltonikaIoWikiRepository extends MongoRepository<TeltonikaIoWikiModel, String> { }
