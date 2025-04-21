package com.xenya52.fmc003_rest_api.entity.model.carDongle;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TeltonikaDongleIoWikiMapping {

    private Map<String, String> wikiIdAndDongleValues;

    public TeltonikaDongleIoWikiMapping(
        Map<String, String> wikiIdAndDongleValues
    ) {
        this.wikiIdAndDongleValues = Collections.unmodifiableMap(
            new HashMap<>(wikiIdAndDongleValues)
        );
        this.wikiIdAndDongleValues = wikiIdAndDongleValues;
    }

    public String getDongleValue(String wikiId) {
        return wikiIdAndDongleValues.get(wikiId);
    }

    public Map<String, String> getAllMappings() {
        return wikiIdAndDongleValues;
    }

    @Override
    public String toString() {
        return (
            "IoWikiMapping{" +
            "wikiIdAndDongleValues=" +
            wikiIdAndDongleValues +
            '}'
        );
    }
}
