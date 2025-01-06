package com.xenya52.fmc003_rest_api.model;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "ioWikiIdAndName")
public class IoWikiModel {

    // Attributes
    @Id
    private String id;

    private String name;

    // Constructors
    public IoWikiModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Methods
    // Todo might not needed
    public String toJson() {
        return (
            "{" +
            "\"id\": \"" +
            this.id +
            "\"," +
            "\"name\": \"" +
            this.name +
            "\"" +
            "}"
        );
    }
}
