package se.nimlab.hl.contract;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CreateReadingDTO {

    @ApiModelProperty(
            value = "The secret key to a the registered device",
            example = "setecastronomy",
            required = true)
    private String secretKey;

    @ApiModelProperty(
            value = "The humidity given in percent, eg. 40.6",
            example = "40.6",
            required = true)
    private Double humidity;

    @ApiModelProperty(
            value = "The temperature given in celsius",
            example = "22.4",
            required = true)
    private Double temperature;
}
