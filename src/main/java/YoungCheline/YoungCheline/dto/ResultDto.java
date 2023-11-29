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
public class ResultDto {
    private String flavor;
    private String cleanliness;
    private String service;
    private String price;
    private String plating;
    private List<String> mood;
}
