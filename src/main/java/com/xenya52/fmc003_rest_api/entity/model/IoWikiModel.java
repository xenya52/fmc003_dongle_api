package com.xenya52.fmc003_rest_api.entity.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Getter
@NoArgsConstructor
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
        this.wikiId = wikiId;
        this.wikiName = wikiName;
        this.wikiDescription = wikiDescription;
        this.wikiType = wikiType;
        this.multiplier = multiplier;
        this.valMin = valMin;
        this.valMax = valMax;
        this.unit = unit;
    }

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
    public String getWikiName() {
        return wikiName;
    }

    public String toJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (Exception e) {
            // Todo
            e.printStackTrace();
            return null;
        }
    }
}
