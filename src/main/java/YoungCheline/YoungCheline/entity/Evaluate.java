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
public class Evaluate {

    @Id
    Key key;
    String restaurantId;
    String flavor;
    String price;
    String couple;
    String family;
    String solo;
    String drink;
    String friend;
    String cleaning;
    String plating;
    String service;
    String url;
    Integer id;
}
