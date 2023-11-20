package YoungCheline.YoungCheline.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KakaoMapDto {

    private String place_name;
    private String restaurantId;
    private String road_address_name;
    private String x;
    private String y;


}
