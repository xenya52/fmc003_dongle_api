package com.xenya52.fmc003_rest_api.entity.builder;

import com.xenya52.fmc003_rest_api.entity.model.IoDongleModel;
import java.util.Map;

public class TeltonikaDongleDataBuilder implements IBuilder {

    private String id;
    private Map<String, String> wikiIdAndDongleValues;

    @Override
    public void reset() {
        this.id = null;
        this.wikiIdAndDongleValues = null;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void setDongleIdsAndVales(
        Map<String, String> wikiIdAndDongleValues
    ) {
        this.wikiIdAndDongleValues = wikiIdAndDongleValues;
    }

    public IoDongleModel getResult() {
        return new IoDongleModel(id, wikiIdAndDongleValues);
    }
}
