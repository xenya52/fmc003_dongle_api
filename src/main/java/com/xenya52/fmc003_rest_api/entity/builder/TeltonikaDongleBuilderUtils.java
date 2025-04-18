package com.xenya52.fmc003_rest_api.entity.builder;

import com.xenya52.fmc003_rest_api.entity.model.IoWikiModel;

public class TeltonikaDongleBuilderUtils {

    /**
     * Generates a random ID as a string.
     * @return a random ID as a string
     */
    private String createRandomDongleID() {
        int random = (int) (Math.random() * 1000000);
        return String.valueOf(random);
    }

    /**
     * Generates random IoWiki values for the given wiki IDs

     * @param wikiIds the list of wiki IDs to generate values for
     * @param ioWikiAmount the number of IoWikiModels to add to the IoDongleModel

     * @return a map of wiki IDs and their corresponding random values
    */
    private List<IoWikiModel> generateIoWikiListByFile(int ioWikiAmount) {
        String id = createRandomDongleID();
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

    /**
     * Constructs a Teltonika dongle with the given builder.
     *
     * @param builder The builder to use for constructing the dongle.
     */
    public void constructRandomDongleWithSpecificAmountIoWikiValues(
        IBuilder builder,
        int ioWikiAmount
    ) {
        builder.reset();
        builder.setId(createRandomDongleID());

        List<IoWikiModel> ioWikiModels = generateIoWikiListByFile(ioWikiAmount);
        Map<String, String> wikiIdAndDongleValues = new HashMap<>();

        for (IoWikiModel wikiModel : ioWikiModels) {
            wikiIdAndDongleValues.put(
                wikiModel.getWikiId(),
                wikiModel.getWikiName()
            );
        }
        builder.setDongleIdsAndVales(wikiIdAndDongleValues);
    }
}
