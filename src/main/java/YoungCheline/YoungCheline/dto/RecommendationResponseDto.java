package YoungCheline.YoungCheline.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationResponseDto {
    private RecommendDto[] recommend;
    private RecommendDto[] hotMenu;
    private RecommendDto[] steadyMenu;
}
