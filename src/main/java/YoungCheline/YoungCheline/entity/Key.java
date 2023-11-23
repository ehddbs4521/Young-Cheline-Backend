package YoungCheline.YoungCheline.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@Embeddable
public class Key implements Serializable {
    private String userName;
    private Integer menuId;
    private String time;
}