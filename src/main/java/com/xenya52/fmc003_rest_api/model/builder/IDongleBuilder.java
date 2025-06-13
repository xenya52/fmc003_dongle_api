package com.xenya52.fmc003_rest_api.entity.model.builder;

import java.util.List;

import com.xenya52.fmc003_rest_api.entity.model.IDongleModel;
import com.xenya52.fmc003_rest_api.entity.model.IWikiValueInDongleModel;

public interface IDongleBuilder {

    void setDongleId(long id);
    void setWikiValues(List<IWikiValueInDongleModel> wikiValues);
    IDongleModel getResult();
}
