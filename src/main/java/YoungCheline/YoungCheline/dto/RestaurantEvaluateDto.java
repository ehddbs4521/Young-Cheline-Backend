package YoungCheline.YoungCheline.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantEvaluateDto {
    Integer menuId;
    String restaurantId;
    String menuName;
    ResultDto evaluate;
    String url;
    Integer id;
    boolean last;
}