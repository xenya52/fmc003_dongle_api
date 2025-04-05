package com.xenya52.fmc003_rest_api.entity.builder;

import com.xenya52.fmc003_rest_api.entity.builder.IBuilder;

public class Director {

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
    TODO see factory

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
        builder.setIoWikiList(null);
    }
}
