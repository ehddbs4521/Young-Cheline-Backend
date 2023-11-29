package YoungCheline.YoungCheline.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TopTenDto {

    Integer menuId;
    String restaurantId;
    String menuName;
    ResultDto evaluate;
    String url;
    String rank;

}
