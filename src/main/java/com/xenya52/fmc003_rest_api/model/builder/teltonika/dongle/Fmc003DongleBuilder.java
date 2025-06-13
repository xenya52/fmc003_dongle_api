package com.xenya52.fmc003_rest_api.entity.model.builder.teltonika.dongle;

import java.util.List;

import com.xenya52.fmc003_rest_api.entity.model.IDongleModel;
import com.xenya52.fmc003_rest_api.entity.model.IWikiValueInDongleModel;
import com.xenya52.fmc003_rest_api.entity.model.builder.IDongleBuilder;

import com.xenya52.fmc003_rest_api.entity.model.teltonika.dongle.TeltonikaFmc003DongleModel;

import lombok.Setter;

@Setter
public class Fmc003DongleBuilder implements IDongleBuilder {
    private long dongleId;
    private List<IWikiValueInDongleModel> wikiValues;

    @Override
    public IDongleModel getResult() {

        return new TeltonikaFmc003DongleModel(
            dongleId,
                wikiValues
                        .stream()
                        .map(v -> (com.xenya52.fmc003_rest_api.entity.model.teltonika.dongle.IoWikiValueInFmc003DongleModel) v)
                        .toList()
        );
    }
}
