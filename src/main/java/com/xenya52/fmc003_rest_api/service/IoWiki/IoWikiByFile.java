package com.xenya52.fmc003_rest_api.service.IoWiki;

import com.xenya52.fmc003_rest_api.entity.model.IoWikiModel;
import java.io.File;
import java.io.FileNotFoundException;
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
