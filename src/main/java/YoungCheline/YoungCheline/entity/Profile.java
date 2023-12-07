package YoungCheline.YoungCheline.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Profile {

    @Id
    private String userName;
    private String url;

}
