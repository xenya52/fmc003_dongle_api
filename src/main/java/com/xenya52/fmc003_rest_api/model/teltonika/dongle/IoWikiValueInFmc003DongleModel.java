package com.xenya52.fmc003_rest_api.entity.model.teltonika.dongle;

import com.xenya52.fmc003_rest_api.entity.model.IWikiValueInDongleModel;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;

@Getter
@Document(collection = "io_dongle_wiki_value")
public class IoWikiValueInFmc003DongleModel implements IWikiValueInDongleModel {
    @Id
    private long id;
    private long dongleId; // Reference to IoDongleModel.deviceId
    private long wikiId;   // Reference to IoWikiModel.wikiId
    private String wikiValue;

    @Override
    public void setWikiValue(String wikiValue) {
        this.wikiValue = wikiValue;
    }
}