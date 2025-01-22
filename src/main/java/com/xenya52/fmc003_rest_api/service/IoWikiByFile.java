package com.xenya52.fmc003_rest_api.service;

import com.xenya52.fmc003_rest_api.entity.model.IoWikiModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class IoWikiByFile {

    // Attributes

    private List<IoWikiModel> idsAndNames;

    // Constructors
    public IoWikiByFile() {
        this.idsAndNames = fetchIdsAndNames();
    }

    // Methods
    public List<IoWikiModel> getIdsAndNames() {
        return idsAndNames;
    }

    /***
     * Returns the name of the property
     * based on the ID
     * @param id
     * @return a string containing the name of the property id
     */
    public String idToName(int id) {
        String name = idsAndNames.get(id).getWikiName();
        return name == null ? "Property not found" : name;
    }

    /***
     * Returns the ID of the property
     * based on the name
     * @param name
     * @return a string containing the ID of the property
     */
    public String nameToId(String name) {
        for (IoWikiModel ioWikiModel : idsAndNames) {
            if (ioWikiModel.getWikiName().equals(name)) {
                return String.valueOf(ioWikiModel.toJson());
            }
        }
        return "Property not found";
    }

    // Todo find a way to scrape from teltonika wiki
    /***
     * Fetches the IDs and names of the properties
     * from the dataSendingParameters.txt file
     * @return a dictionary containing the IDs and names
     */
    private List<IoWikiModel> fetchIdsAndNames() {
        List<IoWikiModel> content = new ArrayList<>();
        String filePath = "src/main/resources/teltonikaIdAndName.txt";
        try {
            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);
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
            myReader.close();
        } catch (FileNotFoundException e) {
            // Todo make it more specific
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return content;
    }
}
