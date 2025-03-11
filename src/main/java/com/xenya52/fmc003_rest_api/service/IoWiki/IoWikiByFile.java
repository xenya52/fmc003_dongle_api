package com.xenya52.fmc003_rest_api.service.IoWiki;

import com.xenya52.fmc003_rest_api.entity.model.IoWikiModel;
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
public class IoWikiByFile {

    private static final Logger LOGGER = Logger.getLogger(
        IoWikiByFile.class.getName()
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
    public List<IoWikiModel> advancedDongleModelsByFile() {
        List<IoWikiModel> ioWikiResponses = new ArrayList<>();

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

    /**
     * Reads and processes the dongle models from a file.
     *
     * @return a list of IoWikiModel objects containing the dongle models.
     */
    public List<IoWikiModel> dongleModelsByFile() {
        List<IoWikiModel> content = new ArrayList<>();
        String filePath = defaultFilePath;

        try (Scanner myReader = new Scanner(new File(filePath))) {
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                String[] parts = line.split(",");

                for (String part : parts) {
                    String[] items = part.split("=");

                    if (items.length == 2) {
                        IoWikiModel model = new IoWikiModel(
                            items[0].replace("{", "").replace(" ", ""),
                            items[1].replace("}", "")
                        );
                        content.add(model);
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
        }
        return content;
    }
}
