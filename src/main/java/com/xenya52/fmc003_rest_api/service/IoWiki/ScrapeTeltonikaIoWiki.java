package com.xenya52.fmc003_rest_api.service.IoWiki;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @class ScrapeTeltonikaIoWiki
 * @brief This class is responsible for scraping the Teltonika wiki and returning the data sending parameters.
 */
@Component
public class ScrapeTeltonikaIoWiki {

    private static final Logger LOGGER = Logger.getLogger(
        ScrapeTeltonikaIoWiki.class.getName()
    );

    // Attributes
    boolean allreadyFetchedthisDay = true;

    final String currentFile = "src/main/resources/dataSendingParameters.txt";
    final String backupFile =
        "src/main/resources/dataSendingParameters_backup.txt";
    final String teltonikaUrl =
        "https://wiki.teltonika-gps.com/view/FMC003_Teltonika_Data_Sending_Parameters_ID";

    /**
     * @brief Resets the allreadyFetchedthisDay flag to false at a scheduled time.
     */
    @Scheduled(cron = "* * 12 * * *", zone = "Europe/Berlin")
    private void resetAllreadyFetchedthisDay() {
        LOGGER.log(
            Level.INFO,
            "Resetting allreadyFetchedthisDay flag to false"
        );
        allreadyFetchedthisDay = false;
        LOGGER.log(Level.INFO, "Value = " + allreadyFetchedthisDay);
    }

    /**
     * @brief Reads the data sending parameters from a file.
     * @return A string containing the data sending parameters.
     */
    private String getdataSendingParametersFromFile() {
        String dataSendingParameters = "";
        try (
            BufferedReader reader = new BufferedReader(
                new FileReader(currentFile)
            )
        ) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            dataSendingParameters = sb.toString();
            reader.close();
        } catch (IOException e) {
            LOGGER.log(
                Level.WARNING,
                "Error reading data sending parameters from file",
                e
            );
        } catch (NullPointerException npe) {
            LOGGER.log(
                Level.SEVERE,
                "NullPointerException: The id is not valid. Please provide a valid id: {0}",
                npe.getMessage()
            );
            throw new IllegalArgumentException(
                "The id is not valid. Please provide a valid id.",
                npe
            );
        }
        return dataSendingParameters;
    }

    /**
     * @brief Fetches the data sending parameters from the Teltonika wiki.
     * @return A string containing the data sending parameters.
     */
    private String fetchdataSendingParametersFromTeltonikaIo() {
        if (!allreadyFetchedthisDay) {
            try {
                String cssQuiery = "tbody";
                Document doc = Jsoup.connect(teltonikaUrl).get();
                allreadyFetchedthisDay = true;

                Elements body = doc.select(cssQuiery);
                Elements rows = body.select("tr");

                return rows.toString();
            } catch (IOException e) {
                LOGGER.log(
                    Level.SEVERE,
                    "Error fetching data sending parameters from Teltonika Io",
                    e
                );
                return e.getMessage();
            } catch (NullPointerException npe) {
                LOGGER.log(
                    Level.SEVERE,
                    "NullPointerException: The id is not valid. Please provide a valid id: {0}",
                    npe.getMessage()
                );
                throw new IllegalArgumentException(
                    "The id is not valid. Please provide a valid id.",
                    npe
                );
            }
        } else {
            LOGGER.log(
                Level.INFO,
                "Data already fetched today, reading from file"
            );
            return getdataSendingParametersFromFile();
        }
    }

    /**
     * @brief Fetches the Teltonika Io wiki data and writes it into a file.
     * @return A boolean indicating whether the data has changed and been updated.
     */
    public boolean fetchTeltonikaIoWikiIntoFile() {
        String newDataSendingParameters =
            fetchdataSendingParametersFromTeltonikaIo();
        String oldDataSendingParameters = getdataSendingParametersFromFile();

        boolean dataHasChanged =
            !oldDataSendingParameters.equals(newDataSendingParameters) &&
            newDataSendingParameters.length() > 0;

        if (dataHasChanged) {
            LOGGER.log(Level.INFO, "Data has changed, updating the file");
            // Backup oldDataSendingParameters
            try (
                BufferedWriter backupWriter = new BufferedWriter(
                    new FileWriter(backupFile)
                )
            ) {
                backupWriter.write(oldDataSendingParameters);
                backupWriter.close();

                LOGGER.log(
                    Level.INFO,
                    "Data has been successfully written to the backup file"
                );
            } catch (IOException e) {
                LOGGER.log(
                    Level.WARNING,
                    "Error writing backup data sending parameters to file",
                    e
                );
            } catch (NullPointerException npe) {
                LOGGER.log(
                    Level.SEVERE,
                    "NullPointerException: The id is not valid. Please provide a valid id: {0}",
                    npe.getMessage()
                );
                throw new IllegalArgumentException(
                    "The id is not valid. Please provide a valid id.",
                    npe
                );
            }

            // Write new dataSendingParameters to current file
            try (
                BufferedWriter currentWriter = new BufferedWriter(
                    new FileWriter(currentFile)
                )
            ) {
                currentWriter.write(newDataSendingParameters);
                currentWriter.close();

                LOGGER.log(
                    Level.INFO,
                    "Data has been successfully written to the file"
                );
                return true;
            } catch (IOException e) {
                LOGGER.log(
                    Level.SEVERE,
                    "Error writing data sending parameters to file",
                    e
                );
            } catch (NullPointerException npe) {
                LOGGER.log(
                    Level.SEVERE,
                    "NullPointerException: The id is not valid. Please provide a valid id: {0}",
                    npe.getMessage()
                );
                throw new IllegalArgumentException(
                    "The id is not valid. Please provide a valid id.",
                    npe
                );
            }
        }
        return false;
    }
}
