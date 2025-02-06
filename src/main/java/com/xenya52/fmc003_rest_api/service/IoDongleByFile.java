package com.xenya52.fmc003_rest_api.service;

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
            String jsonString = scanner.nextLine();

            // Debug
            // See this in console its important for the debugging process
            System.out.println("JSON String: " + jsonString);

            List<String> base64Strings = parseJsonBody(jsonString);

            for (String base64String : base64Strings) {
                List<IoWikiModel> content = encodeBase64(base64String);
                dongleModel.add(new IoDongleModel(content));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return dongleModel;
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

        ObjectMapper objectMapper = new ObjectMapper();
        List<IoWikiModel> content = new ArrayList<>();

        try {
            Scanner myReader = new Scanner(decodedString);
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
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return content;
    }
}
