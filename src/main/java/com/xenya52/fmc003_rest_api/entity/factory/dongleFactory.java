package com.xenya52.fmc003_rest_api.entity.factory;

import com.xenya52.fmc003_rest_api.entity.model.IoDongleModel;
import com.xenya52.fmc003_rest_api.entity.model.IoWikiModel;
import java.util.List;
import java.util.Map;

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
     * Generates a random ID as a string.
     * @return a random ID as a string
     */
    private String createRandomID() {
        int random = (int) (Math.random() * 1000000);
        return String.valueOf(random);
    }

    /**
     * Generates a new IoDongleModel entity with the given parameters.
     * @param ioWikiAmount the number of IoWikiModels to add to the IoDongleModel
     * @return a new IoDongleModel entity with the given parameters
     */
    public IoDongleModel generateIoDongleModelWithNoneValues(int ioWikiAmount) {
        String id = createRandomID();
        List<IoWikiModel> wikiModels = wikiModelsDatabase;

        IoDongleModel dongleModel = new IoDongleModel();
        dongleModel.setDeviceId(id);
        int addedCount = 0;
        for (IoWikiModel wikiModel : wikiModels) {
            if (addedCount >= ioWikiAmount) {
                break;
            }
            if (Math.random() > 0.5) {
                dongleModel.addIoWikiModel(wikiModel.getWikiId(), "0"); // TODO make this more flexible
                addedCount++;
            }
        }
        return dongleModel;
    }

    /**
     * Manually creates a new IoDongleModel entity with the given wiki IDs and values.
     * @param wikiIdsAndValues a map of wiki IDs and their corresponding values
     * @return a new IoDongleModel entity with the given wiki IDs and values
     */
    public IoDongleModel manuallyCreateDongleModel(
        Map<String, String> wikiIdsAndValues
    ) {
        IoDongleModel dongleModel = new IoDongleModel();
        for (Map.Entry<String, String> entry : wikiIdsAndValues.entrySet()) {
            dongleModel.addIoWikiModel(entry.getKey(), entry.getValue());
        }
        return dongleModel;
    }
}
