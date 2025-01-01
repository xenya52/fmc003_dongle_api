package com.xenya52.fmc003_rest_api.service.scrape;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class TeltonikaIoPage {

    private String teltonikaIoData;

    public TeltonikaIoPage() {
        this.teltonikaIoData = fetchTeltonikaIoData();
    }

    public String getIoData() {
        return teltonikaIoData;
    }

    // Get data from TeltonikaIo
    private String fetchTeltonikaIoData() {
        try {
            Document doc = Jsoup.connect(
                "https://wiki.teltonika-gps.com/view/FMC003_Teltonika_Data_Sending_Parameters_ID"
            ).get();
            Element body = doc.body();
            return body.text();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
