package com.xenya52.fmc003_rest_api.entity.factory;

import com.xenya52.fmc003_rest_api.entity.model.IoDongleModel;
import com.xenya52.fmc003_rest_api.entity.model.IoWikiModel;
import java.util.List;
import org.checkerframework.checker.units.qual.A;

/**
 * Factory class for creating IoDongleModel entities.
 */
public class DongleFactory {

    // Attributes
    private List<IoWikiModel> wikiModelsDatabase;

    // Constructor
    public DongleFactory(List<IoWikiModel> wikiModelsDatabase) {
        this.wikiModelsDatabase = wikiModelsDatabase;
    }

    // Methods

    /**
     * Todo
     */
    private String createRandomID() {
        // Get random number
        int random = (int) (Math.random() * 1000000);
        return String.valueOf(random);
    }

    /**
     * Generates a new IoDongleModel entity with the given parameters.
     * @param id the id of the entity
     * @param name the name of the entity
     * @param wikiId the wikiId of the entity
     * @return a new IoDongleModel entity with the given parameters
     */
    public IoDongleModel generateIoDongleModel() {
        String id = createRandomID();
        List<IoWikiModel> wikiModels = this.wikiModels;

        IoDongleModel dongleModel = new IoDongleModel();
        dongleModel.setId(id);
        dongleModel.setName(name);
        for (IoWikiModel wikiModel : wikiModels) {
            if (wikiModel.getId() == wikiId) {
                dongleModel.setWikiModel(wikiModel);
                break;
            }
        }
        return dongleModel;
    }

    /**
     * Generates a new IoDongleModel entity.
     * @return a new IoDongleModel entity
     */
    public IoDongleModel generateIoDongleModel() {
        return new IoDongleModel();
    }
}
