package com.xenya52.fmc003_rest_api.entity.builder;

import java.util.Map;

public interface IBuilder {
    public void reset();

    public void setId(String id);

    public void setIoWikiList(Map<String, String> wikiIdAndDongleValues);
}
