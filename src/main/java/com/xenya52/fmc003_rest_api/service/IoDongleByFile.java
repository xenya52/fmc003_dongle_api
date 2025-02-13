package com.xenya52.fmc003_rest_api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xenya52.fmc003_rest_api.controller.v1.DongleController;
import com.xenya52.fmc003_rest_api.controller.v1.IoWikiController;
import com.xenya52.fmc003_rest_api.entity.model.IoDongleModel;
import com.xenya52.fmc003_rest_api.entity.model.IoWikiModel;
import com.xenya52.fmc003_rest_api.repository.IoWikiRepository;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class IoDongleByFile {

    // Attributes
    private List<IoDongleModel> dongelModel;

    @Autowired
    IoWikiRepository ioWikiRepository;

    // Constructors
    public IoDongleByFile() {
        this.dongelModel = fetchDongleModel();

        // Debug
        System.out.println("IoDongleByFile:");
        for (IoDongleModel dongleModel : dongelModel) {
            System.out.println(dongleModel.toString());
        }
    }

    // Methods
    public List<IoDongleModel> getDongleList() {
        return dongelModel;
    }

    private List<IoDongleModel> fetchDongleModel() {
        String filePath = "src/main/resources/teltonikaDongleDataDumby.txt";

        List<IoDongleModel> dongleList = new ArrayList<>();

        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            StringBuilder jsonStringBuilder = new StringBuilder();

            while (scanner.hasNextLine()) {
                jsonStringBuilder.append(scanner.nextLine());
            }
            String jsonString = jsonStringBuilder.toString();

            List<String> base64Strings = parseJsonBody(jsonString);

            List<Map<IoWikiModel, String>> dongleIdsAndValues = decodeBase64(
                base64Strings
            );

            for (Map<
                IoWikiModel,
                String
            > dongleIdAndValue : dongleIdsAndValues) {
                IoDongleModel dongleModel = new IoDongleModel(dongleIdAndValue);
                dongleList.add(dongleModel);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            return dongleList;
        }
    }

    private static List<String> parseJsonBody(String jsonString) {
        List<String> base64Strings = new ArrayList<>();
        // Parse the JSON string
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

            // Iterate through each JSON object in the array
            for (Map<String, String> dataObject : dataArray) {
                String base64String = dataObject.get("Body");

                // Add the base64 string to the list
                base64Strings.add(base64String);
            }
        } catch (Exception e) {
            // Debug
            System.out.println("Error: " + e.getMessage());

            e.printStackTrace();
        }
        return base64Strings;
    }

    private IoWikiModel getIowikiModelById(String id) {
        Optional<IoWikiModel> ioWikiModel = ioWikiRepository.findById(id);
        return ioWikiModel.orElse(null);
    }

    private List<Map<IoWikiModel, String>> decodeBase64(
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
                // Todo - Handle exception better
                System.out.println(
                    "Error decoding Base64 string: " + e.getMessage()
                );
                e.printStackTrace();
            }
        }

        // Debug
        System.out.println("Decoded List:");
        for (String decodedString : decodedList) {
            System.out.println(decodedString);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<IoWikiModel, String>> dongleIdsAndValues = new ArrayList<>();

        for (String decodedString : decodedList) {
            try {
                Map<String, Object> decodedMap = objectMapper.readValue(
                    decodedString,
                    new TypeReference<Map<String, Object>>() {}
                );

                // Todo fix Java: Type safety: Unchecked cast from Object to Map<String,Object>
                Map<IoWikiModel, String> dongleIdAndValue = new HashMap<>();
                if (decodedMap.containsKey("state")) {
                    Map<String, Object> stateMap = (Map<
                            String,
                            Object
                        >) decodedMap.get("state");
                    if (stateMap.containsKey("reported")) {
                        Map<String, Object> reportedMap = (Map<
                                String,
                                Object
                            >) stateMap.get("reported");
                        for (Map.Entry<
                            String,
                            Object
                        > entry : reportedMap.entrySet()) {
                            dongleIdAndValue.put(
                                new IoWikiModel(entry.getKey(), "Debug"),
                                entry.getValue().toString()
                            );
                        }
                    }
                }
                dongleIdsAndValues.add(dongleIdAndValue);

                // Debug
                System.out.println("DonglesIdsAndValues:");
                for (Map<
                    IoWikiModel,
                    String
                > dongleIdAndValuee : dongleIdsAndValues) {
                    System.out.println(dongleIdAndValuee.toString());
                }
            } catch (JsonProcessingException e) {
                // Todo - Handle exception better
                System.out.println(
                    "Error parsing JSON string: " + e.getMessage()
                );
                e.printStackTrace();
            }
        }
        return dongleIdsAndValues;
    }
}
