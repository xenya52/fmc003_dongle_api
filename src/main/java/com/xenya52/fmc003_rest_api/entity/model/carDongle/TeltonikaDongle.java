package com.xenya52.fmc003_rest_api.entity.model.carDongle;

public class TeltonikaDongle {

    private String deviceId;
    private TeltonikaDongleIoWikiMapping wikiMapping;

    // Private constructor to enforce the use of the builder
    private TeltonikaDongle(
        String deviceId,
        TeltonikaDongleIoWikiMapping wikiMapping
    ) {
        this.deviceId = deviceId;
        this.wikiMapping = wikiMapping;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public TeltonikaDongleIoWikiMapping getWikiMapping() {
        return wikiMapping;
    }

    @Override
    public String toString() {
        return (
            "IoDongleModel{" +
            "deviceId='" +
            deviceId +
            '\'' +
            ", wikiMapping=" +
            wikiMapping +
            '}'
        );
    }

    // Static nested Builder class
    public static class Builder {

        private String deviceId;
        private TeltonikaDongleIoWikiMapping wikiMapping;

        public Builder setDeviceId(String deviceId) {
            this.deviceId = deviceId;
            return this;
        }

        public Builder setWikiMapping(
            TeltonikaDongleIoWikiMapping wikiMapping
        ) {
            this.wikiMapping = wikiMapping;
            return this;
        }

        public TeltonikaDongle build() {
            return new TeltonikaDongle(deviceId, wikiMapping);
        }
    }
}
