package com.xenya52.fmc003_rest_api.entity.model.teltonika.dongle;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;

import com.xenya52.fmc003_rest_api.entity.model.IDongleModel;


@Getter
@Document(collection = "io_dongle")
public class IoDongleModel implements IDongleModel {
    @Id
    private String id;
    private List<IoDongleWikiValue> wikiValues;
    // getters, setters, constructors
}