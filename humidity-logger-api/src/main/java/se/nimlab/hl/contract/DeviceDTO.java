package se.nimlab.hl.contract;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class DeviceDTO {
    private long id;

    private String lastInternalIp;

    private String lastExternalIp;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss:SSS")
    private Date lastSeen;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss:SSS")
    private Date created;
}
