package com.xenya52.fmc003_rest_api.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xenya52.fmc003_rest_api.entity.model.IoDongleModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class IoDongleByFile {

    // Attributes
    private List<IoDongleModel> dongelModel;

    // Constructors
    public IoDongleByFile() {
        this.dongelModel = fetchDongleModel();
    }

    // Methods
    private List<IoDongleModel> fetchDongleModel() {
        List<IoDongleModel> dongleModel = new ArrayList<>();
        String filePath = "src/main/resources/teltonikaDongleDataDumby.txt";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String jsonLine = scanner.nextLine();
                // Assuming the JSON object is in a single line
                JsonNode jsonNode = objectMapper.readTree(jsonLine);
                JsonNode dataArray = jsonNode.get("data");
                if (dataArray.isArray()) {
                    for (JsonNode dataObject : dataArray) {
                        String encodedBody = dataObject.get("Body").asText();
                        String decodedBody = decodeBase64(encodedBody);
                        // Process the decodedBody as needed
                        // For example, you can create IoDongleModel objects and add them to the list
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dongleModel;
    }

    private String decodeBase64(String data) {
        byte[] decodedBytes = Base64.getDecoder().decode(data);
        return new String(decodedBytes);
    }
}
