package com.xenya52.fmc003_rest_api.entity.model.builder;

import com.xenya52.fmc003_rest_api.entity.model.IWikiModel;

public interface IWikiBuilder {

    long setWikiId(long id);

    String setName(String name);

    String setDescription(String description);

    IWikiModel getResult();
}
