package com.xenya52.fmc003_rest_api.service.teltonika;

import com.xenya52.fmc003_rest_api.entity.model.teltonika.TeltonikaIoWikiModel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

@Component
public class TeltonikaIoWikiFileService {

    private static final Logger LOGGER = Logger.getLogger(
        TeltonikaIoWikiFileService.class.getName()
    );

    // Attributes
    final String defaultFilePath = "src/main/resources/teltonikaIdAndName.txt";
    final String advancedDefaultFilePath =
        "src/main/resources/dataSendingParameters.txt";

    /**
     * Reads and processes the advanced dongle models from a file.
     *
     * @return a list of IoWikiModel objects containing the advanced dongle models.
     */
    public List<TeltonikaIoWikiModel> advancedDongleModelsByFile() {
        List<TeltonikaIoWikiModel> ioWikiResponses = new ArrayList<>();

        String dataSendingParameters = "";
        try (Scanner scanner = new Scanner(new File(advancedDefaultFilePath))) {
            while (scanner.hasNextLine()) {
                dataSendingParameters +=
                    scanner.nextLine() + System.lineSeparator();
            }
        } catch (FileNotFoundException e) {
            LOGGER.log(
                Level.SEVERE,
                "FileNotFoundException occurred while reading the file: {0}",
                e.getMessage()
            );
            throw new RuntimeException("Failed to read the file", e);
        }

        try (
            BufferedReader bufReader = new BufferedReader(
                new StringReader(dataSendingParameters)
            )
        ) {
            String line = null;

            while ((line = bufReader.readLine()) != null) {
                if (line.contains("<tr>")) {
                    try {
                        long id = Long.parseLong(bufReader
                            .readLine()
                            .split(">")[1].split("<")[0]);
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
                        TeltonikaIoWikiModel ioWikiResponse = new TeltonikaIoWikiModel(
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
                    } catch (ArrayIndexOutOfBoundsException e) {
                        LOGGER.log(
                            Level.WARNING,
                            "ArrayIndexOutOfBoundsException occurred while processing a line: {0}",
                            e.getMessage()
                        );
                    }
                }
            }
        } catch (FileNotFoundException e) {
            LOGGER.log(
                Level.SEVERE,
                "FileNotFoundException occurred while reading the file: {0}",
                e.getMessage()
            );
            throw new RuntimeException("Failed to read the file", e);
        } catch (Exception e) {
            LOGGER.log(
                Level.SEVERE,
                "An error occurred while processing the file: {0}",
                e.getMessage()
            );
            throw new RuntimeException("Failed to process the file", e);
        }
        return ioWikiResponses;
    }
}
