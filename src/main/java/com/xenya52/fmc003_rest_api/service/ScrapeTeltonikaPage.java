package com.xenya52.fmc003_rest_api.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ScrapeTeltonikaPage {

    private String teltonikaIoData;

    public ScrapeTeltonikaPage() {
        this.teltonikaIoData = fetchTeltonikaIoData();
    }

    public String getIoData() {
        return teltonikaIoData;
    }

    // Get data from TeltonikaIo
    private String fetchTeltonikaIoData() {
        try {
            String cssQuiery = "tbody";
            Document doc = Jsoup.connect(
                "https://wiki.teltonika-gps.com/view/FMC003_Teltonika_Data_Sending_Parameters_ID"
            ).get();
            Elements body = doc.select(cssQuiery);
            Elements rows = body.select("tr");
            StringBuilder filteredData = new StringBuilder();
            for (Element row : rows) {
                String rowText = row.text().toLowerCase();
                if (rowText.contains("fmc003") || rowText.contains("fmbxxx")) {
                    filteredData.append(row.toString());
                }
            }
            return filteredData.toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
