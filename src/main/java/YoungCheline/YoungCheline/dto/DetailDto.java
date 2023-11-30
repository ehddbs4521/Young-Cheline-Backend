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
public class DetailDto {

    private Integer menuId;
    private String restaurantId;
    private ResultDto resultDto;
    private String url;
    private Statistic[] statistic;


}
