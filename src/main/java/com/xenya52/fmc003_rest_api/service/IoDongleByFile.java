package com.xenya52.fmc003_rest_api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import com.xenya52.fmc003_rest_api.controller.v1.DongleController;
import com.xenya52.fmc003_rest_api.entity.model.IoDongleModel;
import com.xenya52.fmc003_rest_api.entity.model.IoWikiModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;
import lombok.Getter;
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

        IoWikiModel ioWikiModel = new IoWikiModel("debug_id", "debug_name");
        List<IoWikiModel> wikiModels = new ArrayList<>();
        wikiModels.add(ioWikiModel);

        List<IoDongleModel> dongleModel = new ArrayList<>();
        dongleModel.add(new IoDongleModel(wikiModels));

        // Debugging
        System.out.println("DEBUG");
        System.out.println(dongleModel.get(0).toJson());
        return dongleModel;
    }

    private String decodeBase64(String data) {
        byte[] decodedBytes = Base64.getDecoder().decode(data);
        return new String(decodedBytes);
    }
}
