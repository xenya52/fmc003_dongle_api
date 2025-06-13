package com.xenya52.fmc003_rest_api.entity.model.teltonika.dongle;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;

import com.xenya52.fmc003_rest_api.entity.model.IDongleModel;


@Getter
@AllArgsConstructor
@Document(collection = "io_dongle")
public class TeltonikaFmc003DongleModel implements IDongleModel {
    @Id
    private long id;
    private List<IoWikiValueInFmc003DongleModelModel> wikiValues;
}