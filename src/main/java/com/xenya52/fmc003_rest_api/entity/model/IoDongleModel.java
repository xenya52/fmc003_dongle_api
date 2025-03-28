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

    public void addIoWikiModel(String wikiId, String dongleValue) {
        wikiIdAndDongleValues.put(wikiId, dongleValue);
    }

    // Constructors
    public IoDongleModel(
        String deviceId,
        Map<String, String> wikiIdAndDongleValues
    ) {
        this.deviceId = deviceId;
        this.wikiIdAndDongleValues = wikiIdAndDongleValues;
    }

    // Todo implement a no args constructor
    public IoDongleModel() {}

    // Methods
    public Map<String, String> getIoWikiIdAndDongleValues() {
        return wikiIdAndDongleValues;
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
