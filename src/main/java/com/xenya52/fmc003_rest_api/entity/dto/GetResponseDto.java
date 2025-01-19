package com.xenya52.fmc003_rest_api.entity.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xenya52.fmc003_rest_api.entity.model.IoWikiModel;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GetResponseDto<T> {

    // Attributes
    private String timestamp;
    private String status;
    private String message;
    private IoWikiModel data;

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
}
