package com.xenya52.fmc003_rest_api.entity.model.carDongle.builder;

import com.xenya52.fmc003_rest_api.entity.model.carDongle.TeltonikaDongle;
import com.xenya52.fmc003_rest_api.entity.model.carDongle.TeltonikaDongleIoWikiMapping;

public class TeltonikaDongleManualBuilder implements ITeltonikaDongleBuilder {

    private String deviceId;
    private TeltonikaDongleIoWikiMapping wikiMapping;

    @Override
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public void setWikiMapping(TeltonikaDongleIoWikiMapping wikiMapping) {
        this.wikiMapping = wikiMapping;
    }

    @Override
    public TeltonikaDongle getResult() {
        return new TeltonikaDongle.Builder()
            .setDeviceId(deviceId)
            .setWikiMapping(wikiMapping)
            .build();
    }
}
