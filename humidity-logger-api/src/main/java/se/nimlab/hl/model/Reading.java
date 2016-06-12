package se.nimlab.hl.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity(name="reading")
public class Reading implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private Double humidity;

    @NotNull
    private Double temperature;

    @NotNull
    private Long deviceId;

    @NotNull
    private Date created;
}
