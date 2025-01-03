package com.xenya52.fmc003_rest_api.service;

import java.util.Dictionary;
import java.util.Hashtable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ScrapeTeltonikaIoWiki {

    // Attributes
    private String dataSendingParameters;

    // Constructors
    public ScrapeTeltonikaIoWiki() {
        this.dataSendingParameters = fetchdataSendingParameters();
    }

    // Methods
    public String getDataSendingParameters() {
        return dataSendingParameters;
    }

    /***
     * Returns the name of the property
     * based on the ID
     * @param id
     * @return a string containing the name of the property id
     */
    public String idToName(int id) {
        Dictionary<String, String> idAndNameDict = getIDAndName();
        return idAndNameDict.get(String.valueOf(id));
    }

    /***
     * Returns the ID of the property
     * based on the name
     * @param name
     * @return the ID of the property
     */
    private Dictionary<String, String> getIDAndName() {
        Dictionary<String, String> idAndNameDict = new Hashtable<>();
        System.out.println(dataSendingParameters);
        try {
            String cssQuery = "tbody";
            Document doc = Jsoup.parse(dataSendingParameters);

            Elements body = doc.select(cssQuery);
            Elements rows = body.select("tr");

            for (int i = 0; i < rows.size(); i++) {
                Elements columns = rows.get(i).select("td");
                if (columns.size() >= 2) {
                    String id = columns.get(0).text().trim();
                    String name = columns.get(1).text().trim();
                    idAndNameDict.put(id, name);
                }
            }
        } catch (Exception e) {
            // Handle exception
            e.printStackTrace();
        }
        return idAndNameDict.isEmpty() ? null : idAndNameDict;
    }

    /***
     * Fetches the data sending parameters
     * from the Teltonika wiki
     * @return a string containing the data sending parameters
     */
    private String fetchdataSendingParameters() {
        try {
            String cssQuiery = "tbody";
            Document doc = Jsoup.connect(
                "https://wiki.teltonika-gps.com/view/FMC003_Teltonika_Data_Sending_Parameters_ID"
            ).get();

            Elements body = doc.select(cssQuiery);
            Elements rows = body.select("tr");

            // Debugging
            System.out.println(rows);

            return rows.toString();
        } catch (Exception e) {
            // Todo AuSbAuFaEhIg
            return e.getMessage();
        }
    }
}
