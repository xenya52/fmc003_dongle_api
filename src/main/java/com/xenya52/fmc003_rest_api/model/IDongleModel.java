package com.xenya52.fmc003_rest_api.entity.model;

import java.util.List;

public interface IDongleModel {

    long getId();

    List<com.xenya52.fmc003_rest_api.entity.model.teltonika.dongle.IoWikiValueInFmc003DongleModel> getWikiValues();
}
