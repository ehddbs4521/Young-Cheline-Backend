package YoungCheline.YoungCheline.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@Embeddable
public class MenuKey implements Serializable {
    Integer menuId;
    String time;
}
