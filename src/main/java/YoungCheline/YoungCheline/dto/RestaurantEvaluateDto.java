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
    private Integer menuId;
    private String restaurantId;
    private String menuName;
    private String taste;
    private String cleaning;
    private String service;
    private String price;
    private String plating;
    private List<String> mood;
    private String url;
    private Integer number;
}
