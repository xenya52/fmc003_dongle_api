package com.xenya52.fmc003_rest_api.entity.builder;

import com.xenya52.fmc003_rest_api.entity.model.IoWikiModel;
import com.xenya52.fmc003_rest_api.service.IoWiki.IoWikiByFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Director {

    // Attributes
    private TeltonikaDongleBuilderUtils teltonikaDongleBuilderUtils;

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
        builder.setId(teltonikaDongleBuilderUtils.createRandomDongleID());

        List<IoWikiModel> ioWikiModels =
            teltonikaDongleBuilderUtils.generateIoWikiListByFile(ioWikiAmount);
        Map<String, String> wikiIdAndDongleValues = new HashMap<>();

        for (IoWikiModel wikiModel : ioWikiModels) {
            wikiIdAndDongleValues.put(
                wikiModel.getWikiId(),
                wikiModel.getWikiName()
            );
        }
        builder.setDongleIdsAndVales(wikiIdAndDongleValues);
    }

    public void constructDongleWikiIdsDongleValuesManually(
        IBuilder builder,
        Map<String, String> wikiIdAndDongleValues
    ) {
        builder.reset();
        builder.setId(teltonikaDongleBuilderUtils.createRandomDongleID());
        builder.setDongleIdsAndVales(wikiIdAndDongleValues);
    }
}
