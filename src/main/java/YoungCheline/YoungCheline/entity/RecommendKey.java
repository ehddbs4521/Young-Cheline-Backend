package YoungCheline.YoungCheline.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Setter
@Getter
@Embeddable
public class RecommendKey implements Serializable {
    private String userName;
    private Integer menuId;
    private LocalDate time;
}
