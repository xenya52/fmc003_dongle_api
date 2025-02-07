package com.xenya52.fmc003_rest_api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xenya52.fmc003_rest_api.entity.model.IoDongleModel;
import com.xenya52.fmc003_rest_api.entity.model.IoWikiModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class IoDongleByFile {

    // Attributes
    private List<IoDongleModel> dongelModel;

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

        List<IoDongleModel> dongleModel = new ArrayList<>();

        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            StringBuilder jsonStringBuilder = new StringBuilder();

            while (scanner.hasNextLine()) {
                jsonStringBuilder.append(scanner.nextLine());
            }
            String jsonString = jsonStringBuilder.toString();

            List<String> base64Strings = parseJsonBody(jsonString);

            for (String base64String : base64Strings) {
                List<IoWikiModel> content = encodeBase64(base64String);
                dongleModel.add(new IoDongleModel(content));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            return dongleModel;
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

    private List<IoWikiModel> encodeBase64(String bodyInput) {
        byte[] decodedBytes = Base64.getDecoder().decode(bodyInput);
        String decodedString = new String(decodedBytes);

        // Debug
        System.out.println("Decoded String: " + decodedString);

        ObjectMapper objectMapper = new ObjectMapper();
        List<IoWikiModel> content = new ArrayList<>();

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
                IoWikiModel model = new IoWikiModel(
                    entry.getKey(),
                    entry.getValue().toString()
                );
                content.add(model);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return content;
    }
}
