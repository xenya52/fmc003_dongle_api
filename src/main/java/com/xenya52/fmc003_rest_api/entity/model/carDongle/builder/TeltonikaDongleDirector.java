package com.xenya52.fmc003_rest_api.entity.model.carDongle.builder;

import com.xenya52.fmc003_rest_api.entity.model.carDongle.TeltonikaDongleIoWikiMapping;
import java.util.Map;

public class TeltonikaDongleDirector {

    private final ITeltonikaDongleBuilder builder;

    public TeltonikaDongleDirector(ITeltonikaDongleBuilder builder) {
        this.builder = builder;
    }

    public void constructBasicDongle(
        String deviceId,
        Map<String, String> wikiMappings
    ) {
        builder.setDeviceId(deviceId);
        builder.setWikiMapping(new TeltonikaDongleIoWikiMapping(wikiMappings));
    }
}
