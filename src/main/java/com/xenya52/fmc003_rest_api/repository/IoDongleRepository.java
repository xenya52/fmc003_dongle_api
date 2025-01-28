package com.xenya52.fmc003_rest_api.repository;

import com.xenya52.fmc003_rest_api.entity.model.IoDongleModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IoDongleRepository
    extends MongoRepository<IoDongleModel, String> {
    Optional<IoDongleModel> findByDongleId(String id);
    Optional<IoDongleModel> findByDongleName(String name);
    List<IoDongleModel> findAll();
}
