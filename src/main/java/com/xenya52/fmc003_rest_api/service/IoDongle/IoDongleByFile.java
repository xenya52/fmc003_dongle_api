package com.xenya52.fmc003_rest_api.service.IoDongle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xenya52.fmc003_rest_api.entity.model.IoDongleModel;
import com.xenya52.fmc003_rest_api.repository.IoWikiRepository;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class responsible for handling IoDongle data from a file.
 */
@Component
public class IoDongleByFile {

    // Attributes
    @Autowired
    IoWikiRepository ioWikiRepository;

    // Default file path TODO make this configurable OR a generator for dumb data
    final String defaultFilePath =
        "src/main/resources/teltonikaDongleDataDumby.txt";

    private static final Logger LOGGER = Logger.getLogger(
        IoDongleByFile.class.getName()
    );

    /**
     * Parses the JSON body to extract Base64 encoded strings.
     *
     * @param jsonString the JSON string to parse
     * @return a list of Base64 encoded strings
     */
    private static List<String> parseJsonBody(String jsonString) {
        List<String> base64Strings = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> jsonObject = objectMapper.readValue(
                jsonString,
                Map.class
            );
            @SuppressWarnings("unchecked")
            List<Map<String, String>> dataArray = (List<
                    Map<String, String>
                >) jsonObject.get("data");

            for (Map<String, String> dataObject : dataArray) {
                String base64String = dataObject.get("Body");
                base64Strings.add(base64String);
            }
        } catch (JsonProcessingException e) {
            LOGGER.log(
                Level.SEVERE,
                "Error processing JSON: {0}",
                e.getMessage()
            );
            throw new RuntimeException("Error processing JSON", e);
        }
        return base64Strings;
    }

    /**
     * Decodes a list of Base64 encoded strings.
     *
     * @param base64Strings the list of Base64 encoded strings
     * @return a list of maps containing dongle IDs and their values
     */
    private List<Map<String, String>> decodeBase64List(
        List<String> base64Strings
    ) {
        List<String> decodedList = new ArrayList<>();

        for (String base64String : base64Strings) {
            try {
                base64String = base64String.replaceAll("\\s+", "");
                byte[] decodedBytes = Base64.getDecoder().decode(base64String);
                String decodedString = new String(decodedBytes);
                decodedList.add(decodedString);
            } catch (IllegalArgumentException e) {
                LOGGER.log(
                    Level.SEVERE,
                    "Error decoding Base64 string: {0}",
                    e.getMessage()
                );
                throw new RuntimeException("Error decoding Base64 string", e);
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, String>> dongleIdsAndValues = new ArrayList<>();

        for (String decodedString : decodedList) {
            try {
                Map<String, Object> decodedMap = objectMapper.readValue(
                    decodedString,
                    new TypeReference<Map<String, Object>>() {}
                );

                Map<String, String> dongleIdAndValue = new HashMap<>();
                if (decodedMap.containsKey("state")) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> stateMap = (Map<
                            String,
                            Object
                        >) decodedMap.get("state");
                    if (stateMap.containsKey("reported")) {
                        @SuppressWarnings("unchecked")
                        Map<String, Object> reportedMap = (Map<
                                String,
                                Object
                            >) stateMap.get("reported");
                        for (Map.Entry<
                            String,
                            Object
                        > entry : reportedMap.entrySet()) {
                            dongleIdAndValue.put(
                                entry.getKey(),
                                entry.getValue().toString()
                            );
                        }
                    }
                }
                dongleIdsAndValues.add(dongleIdAndValue);
            } catch (JsonProcessingException e) {
                LOGGER.log(
                    Level.SEVERE,
                    "Error parsing JSON string: {0}",
                    e.getMessage()
                );
                throw new RuntimeException("Error parsing JSON string", e);
            }
        }
        return dongleIdsAndValues;
    }

    /**
     * Reads the file and converts its content to a list of IoDongleModel objects.
     *
     * @return a list of IoDongleModel objects
     */
    public List<IoDongleModel> dongleModelsByFile() {
        List<IoDongleModel> dongleList = new ArrayList<>();
        File file = new File(defaultFilePath);

        try (Scanner scanner = new Scanner(file)) {
            StringBuilder jsonStringBuilder = new StringBuilder();

            while (scanner.hasNextLine()) {
                jsonStringBuilder.append(scanner.nextLine());
            }
            String jsonString = jsonStringBuilder.toString();

            List<String> base64Strings = parseJsonBody(jsonString);

            List<Map<String, String>> dongleIdsAndValues = decodeBase64List(
                base64Strings
            );

            for (Map<String, String> dongleIdAndValue : dongleIdsAndValues) {
                IoDongleModel dongleModel = new IoDongleModel(dongleIdAndValue);
                dongleList.add(dongleModel);
            }
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, "File not found: {0}", e.getMessage());
            throw new RuntimeException("File not found", e);
        }
        return dongleList;
    }
}
