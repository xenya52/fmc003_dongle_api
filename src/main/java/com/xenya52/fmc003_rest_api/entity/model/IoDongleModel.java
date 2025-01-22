package com.xenya52.fmc003_rest_api.entity.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;

@Getter
@AllArgsConstructor
public class IoDongleModel {

    // Attributes
    @Id
    private long deviceId; // Device ID

    private String sasPolicyName; // Device Name
    private List<IoWikiModel> ioWikiModelList; // Params from teltonika io wiki

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
}
