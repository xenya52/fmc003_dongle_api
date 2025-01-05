package com.xenya52.fmc003_rest_api.entity.dto;

public class IoWikiResponse {

    // Attributes
    String id;
    String name;
    String responseType;
    String minVal;
    String maxVal;
    String multiplier;
    String units;
    String description;

    // Methods
    public String toJson() {
        return (
            "{" +
            "\"id\": \"" +
            this.id +
            "\"," +
            "\"name\": \"" +
            this.name +
            "\"," +
            "\"responseType\": \"" +
            this.responseType +
            "\"," +
            "\"minVal\": \"" +
            this.minVal +
            "\"," +
            "\"maxVal\": \"" +
            this.maxVal +
            "\"," +
            "\"multiplier\": \"" +
            this.multiplier +
            "\"," +
            "\"units\": \"" +
            this.units +
            "\"," +
            "\"description\": \"" +
            this.description +
            "\"" +
            "}"
        );
    }

    // Constructors
    public IoWikiResponse(
        String id,
        String name,
        String responseType,
        String minVal,
        String maxVal
    ) {
        this.id = id;
        this.name = name;
        this.responseType = responseType;
        this.minVal = minVal;
        this.maxVal = maxVal;
        this.multiplier = "-";
        this.units = "-";
        this.description = "-";
    }

    public IoWikiResponse(
        String id,
        String name,
        String responseType,
        String minVal,
        String maxVal,
        String multiplier
    ) {
        this.id = id;
        this.name = name;
        this.responseType = responseType;
        this.minVal = minVal;
        this.maxVal = maxVal;
        this.multiplier = multiplier;
        this.units = "-";
        this.description = "-";
    }

    public IoWikiResponse(
        String id,
        String name,
        String responseType,
        String minVal,
        String maxVal,
        String multiplier,
        String units
    ) {
        this.id = id;
        this.name = name;
        this.responseType = responseType;
        this.minVal = minVal;
        this.maxVal = maxVal;
        this.multiplier = multiplier;
        this.units = units;
        this.description = "-";
    }

    public IoWikiResponse(
        String id,
        String name,
        String responseType,
        String minVal,
        String maxVal,
        String multiplier,
        String units,
        String description
    ) {
        this.id = id;
        this.name = name;
        this.responseType = responseType;
        this.minVal = minVal;
        this.maxVal = maxVal;
        this.multiplier = multiplier;
        this.units = units;
        this.description = description;
    }
}
