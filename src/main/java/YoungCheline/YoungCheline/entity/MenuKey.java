package YoungCheline.YoungCheline.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
@Embeddable
public class MenuKey implements Serializable {
    Integer menuId;
    LocalDateTime time;
}
