package YoungCheline.YoungCheline.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantEvaluate {
    @Id
    Integer menuId;
    String restaurantId;
    String menuName;
    String flavor;
    String cleaning;
    String service;
    String price;
    String plating;
    String couple;
    String family;
    String solo;
    String drink;
    String friend;
    String url;
    Integer id;
}
