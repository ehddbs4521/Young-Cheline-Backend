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
    private String flavor;
    private List<String> mood;
    private String price;
    private String cleaning;
    private String plating;
    private String service;
    private Statistic[] statistic;


}
