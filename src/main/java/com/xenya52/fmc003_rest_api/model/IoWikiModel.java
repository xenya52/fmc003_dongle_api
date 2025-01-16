package com.xenya52.fmc003_rest_api.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@AllArgsConstructor
@Document(collection = "ioWikiIdAndName")
public class IoWikiModel {

    // Attributes
    @Id
    private String wikiId;

    @PartitionKey
    private String wikiName;

    private String wikiDescription;
    private String wikiType;
    private String multiplier;
    private String valMin;
    private String valMax;
    private String unit;

    // Constructors
    public IoWikiModel() {
        this.wikiId = "-";
        this.wikiName = "-";
        this.wikiDescription = "-";
        this.wikiType = "-";
        this.multiplier = "-";
        this.valMin = "-";
        this.valMax = "-";
        this.unit = "-";
    }

    // Methods
    public String toJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }
}
