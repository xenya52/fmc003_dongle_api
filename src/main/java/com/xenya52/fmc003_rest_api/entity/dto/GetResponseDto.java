package com.xenya52.fmc003_rest_api.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xenya52.fmc003_rest_api.entity.model.IoWikiModel;
import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetResponseDto {

    // Attributes
    @JsonFormat(
        shape = JsonFormat.Shape.STRING,
        pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
    )
    private Date timestamp;

    private String status;
    private String message;
    private IoWikiModel data;

    // Todo add last Model
    // Todo add prev Model
    private List<Map<String, String>> links;

    //Constructor
    public GetResponseDto(IoWikiModel data_) {
        initResponseDto(data_);
    }

    // Methods
    private boolean initResponseDto(IoWikiModel data_) {
        this.timestamp = new Date();
        this.status = "200";
        this.message = "OK";
        this.data = data_;
        return true;
    }
}
