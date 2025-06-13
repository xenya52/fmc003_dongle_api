package com.xenya52.fmc003_rest_api.entity.model.builder.teltonika.wiki;

import com.xenya52.fmc003_rest_api.entity.model.IWikiModel;
import com.xenya52.fmc003_rest_api.entity.model.teltonika.TeltonikaIoWikiModel;
import com.xenya52.fmc003_rest_api.entity.model.builder.IWikiBuilder;

import lombok.Setter;

@Setter
public class IoWikiBuilder implements IWikiBuilder {
    private long wikiId = 0;
    private String name = "";
    private String description = "";
    private String wikiType;
    private String multiplier;
    private String valMin;
    private String valMax;
    private String unit;

    @Override
    public IWikiModel getResult() {
        return new TeltonikaIoWikiModel(
            wikiId,
            name,
            description,
            wikiType,
            multiplier,
            valMin,
            valMax,
            unit
        );
    }
}
