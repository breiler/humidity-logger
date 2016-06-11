package se.nimlab.hl.contract;

import lombok.Data;

@Data
public class ReadingDTO {
    private Long id;

    private String humidity;

    private String temperature;
}
