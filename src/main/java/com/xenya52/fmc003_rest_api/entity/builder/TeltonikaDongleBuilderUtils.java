package com.xenya52.fmc003_rest_api.entity.builder;

import com.xenya52.fmc003_rest_api.entity.model.IoWikiModel;
import com.xenya52.fmc003_rest_api.service.IoWiki.IoWikiByFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeltonikaDongleBuilderUtils {

    /**
     * Generates a random ID as a string.
     * @return a random ID as a string
     */
    public String createRandomDongleID() {
        int random = (int) (Math.random() * 1000000);
        return String.valueOf(random);
    }

    /**
     * Generates random IoWiki values for the given wiki IDs

     * @param wikiIds the list of wiki IDs to generate values for
     * @param ioWikiAmount the number of IoWikiModels to add to the IoDongleModel

     * @return a map of wiki IDs and their corresponding random values
    */
    public List<IoWikiModel> generateIoWikiListByFile(int ioWikiAmount) {
        IoWikiByFile wikiByFile = new IoWikiByFile();
        List<IoWikiModel> allWikiModels =
            wikiByFile.advancedDongleModelsByFile();
        List<IoWikiModel> finalWikiModels = new ArrayList<>();
        int addedCount = 0;

        for (IoWikiModel wikiModel : allWikiModels) {
            if (addedCount >= ioWikiAmount) {
                break;
            }
            if (Math.random() > 0.5) {
                finalWikiModels.add(wikiModel);
                addedCount++;
            }
        }
        return finalWikiModels;
    }
}
