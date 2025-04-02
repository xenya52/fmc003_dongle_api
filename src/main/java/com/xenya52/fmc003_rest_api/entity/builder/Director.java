package com.xenya52.fmc003_rest_api.entity.builder;

import com.xenya52.fmc003_rest_api.entity.builder.IBuilder;

public class Director {

    private IBuilder builder;

    public Director(IBuilder builder) {
        this.builder = builder;
    }

    public void changwBuilder(IBuilder builder) {
        this.builder = builder;
    }

    public void construct() {
        builder.reset();
        builder.setId("1");
        builder.setName("test");
        builder.setIoWikiList(null);
    }
}
