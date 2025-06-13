package com.xenya52.fmc003_rest_api.entity.model;

public interface IWikiValueInDongleModel {
    long getId();

    long getDongleId();

    long getWikiId();

    String getWikiValue();
    void setWikiValue(String wikiValue);
}
