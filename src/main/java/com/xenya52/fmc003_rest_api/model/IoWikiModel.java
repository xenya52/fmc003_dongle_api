package com.xenya52.fmc003_rest_api.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;

@Getter
public class IoWikiModel {

    // Attributes
    @Id
    private String wikiId;

    private String wikiName;
    private String wikiDescription;
    private String wikiType;
    private String multiplier;
    private String valMin;
    private String valMax;
    private String unit;

    // Constructors
    public IoWikiModel(
        String wikiId,
        String wikiName,
        String wikiDescription,
        String wikiType,
        String multiplier,
        String valMin,
        String valMax,
        String unit
    ) {
        this.wikiId = wikiId != null ? wikiId : "-";
        this.wikiName = wikiName != null ? wikiName : "-";
        this.wikiDescription = wikiDescription != null ? wikiDescription : "-";
        this.wikiType = wikiType != null ? wikiType : "-";
        this.multiplier = multiplier != null ? multiplier : "-";
        this.valMin = valMin != null ? valMin : "-";
        this.valMax = valMax != null ? valMax : "-";
        this.unit = unit != null ? unit : "-";
    }

    // Constructor
    public IoWikiModel(String wikiId, String wikiName) {
        this(wikiId, wikiName, "-", "-", "-", "-", "-", "-");
    }

    public IoWikiModel(String wikiId, String wikiName, String wikiDescription) {
        this(wikiId, wikiName, wikiDescription, "-", "-", "-", "-", "-");
    }

    public IoWikiModel(
        String wikiId,
        String wikiName,
        String wikiDescription,
        String wikiType
    ) {
        this(wikiId, wikiName, wikiDescription, wikiType, "-", "-", "-", "-");
    }

    public IoWikiModel(
        String wikiId,
        String wikiName,
        String wikiDescription,
        String wikiType,
        String multiplier
    ) {
        this(
            wikiId,
            wikiName,
            wikiDescription,
            wikiType,
            multiplier,
            "-",
            "-",
            "-"
        );
    }

    public IoWikiModel(
        String wikiId,
        String wikiName,
        String wikiDescription,
        String wikiType,
        String multiplier,
        String valMin
    ) {
        this(
            wikiId,
            wikiName,
            wikiDescription,
            wikiType,
            multiplier,
            valMin,
            "-",
            "-"
        );
    }

    public IoWikiModel(
        String wikiId,
        String wikiName,
        String wikiDescription,
        String wikiType,
        String multiplier,
        String valMin,
        String valMax
    ) {
        this(
            wikiId,
            wikiName,
            wikiDescription,
            wikiType,
            multiplier,
            valMin,
            valMax,
            "-"
        );
    }

    // Methods
    public String toJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
