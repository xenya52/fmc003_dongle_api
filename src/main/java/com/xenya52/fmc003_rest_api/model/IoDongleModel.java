package main.java.com.xenya52.fmc003_rest_api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.PartitionKey;
import org.springframework.data.mongodb.core.mapping.Document;

@Getters
@AllArgsConstructor
@Document(collection = "ioDongle")
public class IoDongleModel {

    // Attributes
    @Id
    private long deviceId; // Device ID

    @PartitionKey
    private String sasPolicyName; // Device Name

    private List<IoWikiModel> ioWikiModelList; // Params from teltonika io wiki

    // Methods
    public String toJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }

    public bool parseDongleJsonToIoWikiModelList(String json) {
        try {
            // Todo: implement
        } catch (JsonProcessingException e) {
            return false;
        }
        return true;
    }
}
