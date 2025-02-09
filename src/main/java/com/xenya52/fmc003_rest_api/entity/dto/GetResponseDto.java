package com.xenya52.fmc003_rest_api.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xenya52.fmc003_rest_api.entity.model.IIo;
import java.util.Date;
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

    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    private IIo data;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String status;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String message;

    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    private Map<String, String> links;

    //Constructor
    public GetResponseDto(IIo data_, Map<String, String> links_) {
        initResponseDto(data_, links_);
    }

    // Methods
    private boolean initResponseDto(IIo data_, Map<String, String> links_) {
        this.timestamp = new Date();
        this.status = "200";
        this.message = "OK";
        this.data = data_;
        this.links = links_;
        return true;
    }
}
