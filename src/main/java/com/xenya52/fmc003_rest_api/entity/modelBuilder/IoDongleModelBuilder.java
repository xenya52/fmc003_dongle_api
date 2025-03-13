package com.xenya52.fmc003_rest_api.entity.modelBuilder;

import com.xenya52.fmc003_rest_api.entity.model.IoDongleModel;
import java.util.Map;

public class IoDongleModelBuilder {

    // Attributes
    private String deviceId; // Device ID
    private Map<String, String> wikiIdAndDongleValues;

    // Constructors
    public IoDongleModelBuilder() {}

    // Methods
    public IoDongleModelBuilder withDeviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public IoDongleModelBuilder withWikiIdAndDongleValues(
        Map<String, String> wikiIdAndDongleValues
    ) {
        this.wikiIdAndDongleValues = wikiIdAndDongleValues;
        return this;
    }

    public IoDongleModel build() {
        IoDongleModel ioDongleModel = new IoDongleModel();
        ioDongleModel.setDeviceId(deviceId);
        ioDongleModel.setWikiIdAndDongleValues(wikiIdAndDongleValues);
        return ioDongleModel;
    }
}
