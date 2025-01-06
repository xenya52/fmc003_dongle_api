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
    @PartitionKey
    private String deviceId; // Device ID

    private String ts; // Timestamp
    private String pr; // Pressure
    private String latlng; // Latitude and Longitude
    private String alt; // Altitude
    private String ang; // Angle
    private String sat; // Satellites
    private String sp; // Speed
    private String evt; // Event
    private Map<String, String> ioWikiParamas; // Params from teltonika io wiki

    // Methods
    public String toJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }
}
