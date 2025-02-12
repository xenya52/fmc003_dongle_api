package com.xenya52.fmc003_rest_api.entity.model;

import java.util.Map;
import org.springframework.data.annotation.Id;

public class IoDongleModel implements IIo {

    // Attributes
    @Id
    private String deviceId; // Device ID

    private Map<IoWikiModel, String> ioWikiIdAndDongleValues;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    // Constructors
    public IoDongleModel(Map<IoWikiModel, String> ioWikiIdsAndValues) {
        this.deviceId = debugCreateRandomID();
        this.ioWikiIdAndDongleValues = ioWikiIdsAndValues;
    }

    // Methods
    public Map<IoWikiModel, String> getIoWikiIdAndDongleValues() {
        return ioWikiIdAndDongleValues;
    }

    private String debugCreateRandomID() {
        // Get random number
        int random = (int) (Math.random() * 1000000);
        return String.valueOf(random);
    }
}
