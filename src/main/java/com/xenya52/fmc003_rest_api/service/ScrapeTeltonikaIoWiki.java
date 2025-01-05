package com.xenya52.fmc003_rest_api.service;

import com.xenya52.fmc003_rest_api.entity.dto.IoWikiResponse;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Dictionary;
import java.util.Hashtable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/***
 * This class is responsible for scraping the Teltonika wiki
 * and returning the data sending parameters
 */
public class ScrapeTeltonikaIoWiki {

    // Attributes
    private String dataSendingParameters;
    private IoWikiResponse[] dataSendingParametersJson;

    // Constructors
    public ScrapeTeltonikaIoWiki() {
        this.dataSendingParameters = fetchdataSendingParameters();
        this.dataSendingParametersJson = getIoWikiResponses();
    }

    // Methods
    public String getDataSendingParameters() {
        return dataSendingParameters;
    }

    public String getDataSendingParametersJson() {
        // Debugging
        System.out.println(dataSendingParametersJson);

        StringBuilder jsonList = new StringBuilder("[");

        for (int i = 0; i < dataSendingParametersJson.length; i++) {
            // Debugging
            System.out.println(dataSendingParametersJson[i].toJson());

            jsonList.append(dataSendingParametersJson[i].toJson());
            if (i < dataSendingParametersJson.length - 1) {
                jsonList.append(",");
            }
        }
        jsonList.append("]");
        return jsonList.toString();
    }

    /***
     * Returns the name of the property
     * based on the ID
     * @param id
     * @return a string containing the name of the property id
     */
    public String idToName(int id) {
        String name = getIdAndName().get(String.valueOf(id));
        return name == null ? "Property not found" : name;
    }

    /***
     * Returns the ID of the property
     * based on the name
     * @param name
     * @return a string containing the ID of the property
     */
    public String nameToId(String name) {
        Dictionary<String, String> idAndName = getIdAndName();
        java.util.Enumeration<String> keys = idAndName.keys();

        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            String val = idAndName.get(key);

            if (val.equals(name)) {
                return key;
            }
        }
        return "Property not found";
    }

    // Todo
    private boolean doesViolatePrivacyPolicy() {
        return false;
    }

    /***
     * Returns the data sending parameters
     * in JSON format
     * @return a string containing the data sending parameters in JSON format
     */
    private IoWikiResponse[] getIoWikiResponses() {
        // Debugging
        System.out.println(dataSendingParameters);

        IoWikiResponse[] ioWikiResponses = new IoWikiResponse[getIdAndName()
            .size()];
        try {
            BufferedReader bufReader = new BufferedReader(
                new StringReader(dataSendingParameters)
            );
            String Line = null;

            while ((Line = bufReader.readLine()) != null) {
                if (Line.contains("<tr>")) {
                    String id = bufReader
                        .readLine()
                        .split(">")[1].split("<")[0];
                    String name = bufReader
                        .readLine()
                        .split(">")[1].split("<")[0];
                    String responseType = bufReader
                        .readLine()
                        .split(">")[1].split("<")[0];
                    String minVal = bufReader
                        .readLine()
                        .split(">")[1].split("<")[0];
                    String maxVal = bufReader
                        .readLine()
                        .split(">")[1].split("<")[0];
                    String multiplier = bufReader
                        .readLine()
                        .split(">")[1].split("<")[0];
                    String units = bufReader
                        .readLine()
                        .split(">")[1].split("<")[0];
                    String description = bufReader
                        .readLine()
                        .split(">")[1].split("<")[0];
                    IoWikiResponse ioWikiResponse = new IoWikiResponse(
                        id,
                        name,
                        responseType,
                        minVal,
                        maxVal,
                        multiplier,
                        units,
                        description
                    );
                    ioWikiResponses[Integer.parseInt(id)] = ioWikiResponse;
                }
            }
        } catch (Exception e) {
            // Todo AuSbAuFaEhIg
        }
        return ioWikiResponses;
    }

    /***
     * Returns the ID of the property
     * based on the name
     * @param name
     * @return the ID of the property
     */
    private Dictionary<String, String> getIdAndName() {
        Dictionary<String, String> idAndNameDict = new Hashtable<>();
        try {
            BufferedReader bufReader = new BufferedReader(
                new StringReader(dataSendingParameters)
            );
            String Line = null;

            while ((Line = bufReader.readLine()) != null) {
                if (Line.contains("<tr>")) {
                    String key = bufReader
                        .readLine()
                        .split(">")[1].split("<")[0];
                    String value = bufReader
                        .readLine()
                        .split(">")[1].split("<")[0];
                    idAndNameDict.put(key, value);
                }
            }
            idAndNameDict.remove("Property ID in AVL packet");
        } catch (Exception e) {
            // Todo AuSbAuFaEhIg
        }
        System.out.println(idAndNameDict);
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

            return rows.toString();
        } catch (Exception e) {
            // Todo AuSbAuFaEhIg
            return e.getMessage();
        }
    }
}
