package com.xenya52.fmc003_rest_api.entity.factory;

import com.xenya52.fmc003_rest_api.entity.model.IoDongleModel;
import com.xenya52.fmc003_rest_api.entity.model.IoWikiModel;
import java.util.List;

/**
 * Factory class for creating IoDongleModel entities.
 */
public class DongleFactory {

    /**
     * Generates a new IoDongleModel entity.
     * @return a new IoDongleModel entity
     */
    public IoDongleModel generateIoDongleModel() {
        return new IoDongleModel();
    }
}
