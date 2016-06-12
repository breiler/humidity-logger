package se.nimlab.hl.contract;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ReadingDTO {
    private Long id;

    private Long deviceId;

    private Double humidity;

    private Double temperature;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss:SSS")
    private Date created;
}
