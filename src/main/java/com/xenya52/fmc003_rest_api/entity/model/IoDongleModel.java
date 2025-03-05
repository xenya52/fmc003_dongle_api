package com.xenya52.fmc003_rest_api.entity.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.springframework.data.annotation.Id;

public class IoDongleModel implements IIo {

    // Attributes
    @Id
    private String deviceId; // Device ID

    private Map<String, String> wikiIdAndDongleValues;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    // Constructors
    public IoDongleModel(Map<String, String> ioWikiIdsAndValues) {
        this.deviceId = debugCreateRandomID();
        this.wikiIdAndDongleValues = ioWikiIdsAndValues;
    }

    // Todo implement a no args constructor
    public IoDongleModel() {
        this.deviceId = debugCreateRandomID();
    }

    // Methods
    public Map<String, String> getIoWikiIdAndDongleValues() {
        return wikiIdAndDongleValues;
    }

    private String debugCreateRandomID() {
        // Get random number
        int random = (int) (Math.random() * 1000000);
        return String.valueOf(random);
    }

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return (
                "IoDongleModel{deviceId='" +
                deviceId +
                "', ioWikiIdAndDongleValues=" +
                wikiIdAndDongleValues +
                "}"
            );
        }
    }
}
