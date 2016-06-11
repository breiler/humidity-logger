package se.nimlab.hl.contract;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdateDeviceDTO {

    @ApiModelProperty(
            value = "The unique id of the device",
            example = "1",
            required = true)
    private long id;

    @ApiModelProperty(
            value = "The secret key to a the registered device",
            example = "setecastronomy",
            required = true)
    private String secretKey;

    @ApiModelProperty(
            value = "The last known internal IP address of the device",
            example = "192.168.1.1",
            required = false)
    private String lastInternalIp;
}
