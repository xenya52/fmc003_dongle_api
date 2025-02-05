package com.xenya52.fmc003_rest_api.repository;

import com.xenya52.fmc003_rest_api.entity.model.IoDongleModel;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IoDongleRepository
    extends MongoRepository<IoDongleModel, String> {
    Optional<IoDongleModel> findByDeviceId(String id);
    List<IoDongleModel> findAll();
}
