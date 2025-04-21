package com.xenya52.fmc003_rest_api.entity.model.carDongle.builder;

import com.xenya52.fmc003_rest_api.entity.model.carDongle.TeltonikaDongle;
import com.xenya52.fmc003_rest_api.entity.model.carDongle.TeltonikaDongleIoWikiMapping;

public interface ITeltonikaDongleBuilder {
    void setDeviceId(String deviceId);
    void setWikiMapping(TeltonikaDongleIoWikiMapping wikiMapping);
    TeltonikaDongle getResult();
}
