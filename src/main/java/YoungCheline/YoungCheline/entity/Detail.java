package YoungCheline.YoungCheline.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Detail {
    @Id
    Integer menuId;
    Integer avg1;
    Integer avg2;
    String url1;
    String url2;


}
