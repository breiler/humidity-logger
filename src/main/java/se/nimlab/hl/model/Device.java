package se.nimlab.hl.model;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity(name = "device")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty
    private String secretKey;

    @NotEmpty
    private String lastInternalIp;

    @NotEmpty
    private String lastExternalIp;

    @NotNull
    private Date lastSeen;

    @NotNull
    private Date created;
}
