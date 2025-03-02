package com.xenya52.fmc003_rest_api.service.IoWiki;

import com.xenya52.fmc003_rest_api.entity.model.IoWikiModel;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/***
 * This class is responsible for scraping the Teltonika wiki
 * and returning the data sending parameters
 */
public class ScrapeTeltonikaIoWiki {

    // Constructors
    public ScrapeTeltonikaIoWiki() {} // TODO remove this

    // Methods
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

    /***
     * Returns the data sending parameters
     * in IoWikiModel format
     * @return a string containing the data sending parameters in JSON format
     */
    public List<IoWikiModel> getIoWikiListScrapedfromTeltonika() {
        String dataSendingParameters = fetchdataSendingParameters();
        try (
            BufferedWriter writer = new BufferedWriter(
                new FileWriter("src/main/resources/dataSendingParameters.txt")
            )
        ) {
            writer.write(dataSendingParameters);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Debugging
        System.out.println(dataSendingParameters);

        List<IoWikiModel> ioWikiResponses = new ArrayList<>();

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
                    IoWikiModel ioWikiResponse = new IoWikiModel(
                        id,
                        name,
                        responseType,
                        minVal,
                        maxVal,
                        multiplier,
                        units,
                        description
                    );
                    ioWikiResponses.add(ioWikiResponse);
                }
            }
        } catch (Exception e) {
            // Todo AuSbAuFaEhIg
        }
        return ioWikiResponses;
    }
}
