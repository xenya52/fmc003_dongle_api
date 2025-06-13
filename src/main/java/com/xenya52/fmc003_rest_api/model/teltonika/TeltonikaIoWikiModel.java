package com.xenya52.fmc003_rest_api.entity.model.teltonika;

import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;

import com.xenya52.fmc003_rest_api.entity.model.IWikiModel;

@Getter
@AllArgsConstructor
@Document(collection = "io_wiki")
public class TeltonikaIoWikiModel implements IWikiModel {
    @Id
    private long id;
    private String name;
    private String description;
    private String wikiType;
    private String multiplier;
    private String valMin;
    private String valMax;
    private String unit;
}