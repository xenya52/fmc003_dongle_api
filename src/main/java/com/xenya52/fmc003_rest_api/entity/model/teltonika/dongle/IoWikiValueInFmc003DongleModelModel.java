package com.xenya52.fmc003_rest_api.entity.model.teltonika.dongle;

import com.xenya52.fmc003_rest_api.entity.model.IWikiValueInDongle;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;

@Getter
@Document(collection = "io_dongle_wiki_value")
public class Fmc003DongleTeltonikaIoWikiValue implements IWikiValueInDongle {
    @Id
    private long id;
    private long dongleId; // Reference to IoDongleModel.deviceId
    private long wikiId;   // Reference to IoWikiModel.wikiId
    private String value;
}