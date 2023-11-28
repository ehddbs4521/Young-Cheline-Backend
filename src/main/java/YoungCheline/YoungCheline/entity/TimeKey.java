package YoungCheline.YoungCheline.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Setter
@Getter
@Embeddable
public class TimeKey implements Serializable {
    private Integer menuId;
    private LocalDate time;
}
