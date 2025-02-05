package com.xenya52.fmc003_rest_api.entity.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import com.xenya52.fmc003_rest_api.entity.model.IIo;
import java.util.List;
import org.springframework.data.annotation.Id;

public class IoDongleModel implements IIo {

    // Attributes
    @Id
    private String deviceId; // Device ID

    private List<IoWikiModel> ioWikiModelList; // Params from teltonika io wiki

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    // Constructors
    public IoDongleModel(List<IoWikiModel> ioWikiModelList) {
        this.deviceId = debugCreateRandomID();
        this.ioWikiModelList = ioWikiModelList;
    }

    // Methods
    public String toJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean parseDongleJsonToIoWikiModelList(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            this.ioWikiModelList = objectMapper.readValue(
                json,
                objectMapper
                    .getTypeFactory()
                    .constructCollectionType(List.class, IoWikiModel.class)
            );
            return true;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String debugCreateRandomID() {
        // Get random number
        int random = (int) (Math.random() * 1000000);
        return String.valueOf(random);
    }
}
