package com.xenya52.fmc003_rest_api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

            // Debug
            System.out.println(
                "Debug - Base64Strings in ByFile fetchDongleModel"
            );
            for (String base64String : base64Strings) {
                System.out.println("Base64 String: ");
                System.out.println(base64String);
            }

            for (String base64String : base64Strings) {
                // Debug
                System.out.println(
                    "Debug - DongleIdsAndValues in ByFile fetchDongleModel"
                );

                Map<String, String> dongleIdsAndValues = encodeBase64(
                    base64String
                );

                for (String key : dongleIdsAndValues.keySet()) {
                    System.out.println(
                        key + " : " + dongleIdsAndValues.get(key)
                    );
                }

                dongleList.add(new IoDongleModel(dongleIdsAndValues));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            e.printStackTrace();
        } finally {
            // Debug
            System.out.println("Debug - DongleList in ByFile fetchDongleModel");
            for (IoDongleModel dongleModel : dongleList) {
                System.out.println(dongleModel.getDeviceId());
                for (String key : dongleModel
                    .getIoWikiIdAndDongleValues()
                    .keySet()) {
                    System.out.println(
                        key +
                        " : " +
                        dongleModel.getIoWikiIdAndDongleValues().get(key)
                    );
                }
            }

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

    private Map<String, String> encodeBase64(String bodyInput) {
        byte[] decodedBytes = Base64.getDecoder().decode(bodyInput);
        String decodedString = new String(decodedBytes);

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> content = new HashMap<>();

        try {
            Map<String, Object> decodedMap = objectMapper.readValue(
                decodedString,
                Map.class
            );
            Map<String, Object> stateMap = (Map<String, Object>) decodedMap.get(
                "state"
            );
            Map<String, Object> reportedMap = (Map<
                    String,
                    Object
                >) stateMap.get("reported");

            for (Map.Entry<String, Object> entry : reportedMap.entrySet()) {
                content.put(
                    entry.getKey().toString(),
                    entry.getValue().toString()
                );
            }
        } catch (JsonProcessingException e) {
            // Debug
            System.out.println("Error: Here's the error");

            e.printStackTrace();
        }
        return content;
    }
}
