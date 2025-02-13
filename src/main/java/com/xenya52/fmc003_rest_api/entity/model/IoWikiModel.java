package com.xenya52.fmc003_rest_api.entity.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class IoWikiModel implements IIo {

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

    //Debug todo remove this soon
    public IoWikiModel() {
        this("debug", "-", "-", "-", "-", "-", "-", "-");
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

    public String getWikiId() {
        return wikiId;
    }

    public int getWikiIdAsInt() {
        try {
            return Integer.parseInt(wikiId);
        } catch (NumberFormatException e) {
            int result = 0;
            for (char c : wikiId.toCharArray()) {
                result = result * 31 + (int) c;
            }
            return result;
        }
    }

    public String getWikiName() {
        return wikiName;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            // Todo fix debug
            System.out.print("DEBUG");
            return (
                "IoWikiModel{" +
                "wikiId='" +
                wikiId +
                '\'' +
                ", wikiName='" +
                wikiName +
                '\'' +
                ", wikiDescription='" +
                wikiDescription +
                '\'' +
                ", wikiType='" +
                wikiType +
                '\'' +
                ", multiplier='" +
                multiplier +
                '\'' +
                ", valMin='" +
                valMin +
                '\'' +
                ", valMax='" +
                valMax +
                '\'' +
                ", unit='" +
                unit +
                '\'' +
                '}'
            );
        }
    }
}
