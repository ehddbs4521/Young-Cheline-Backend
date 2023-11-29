package YoungCheline.YoungCheline.dto;

import lombok.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SurveyDto {
    Integer menuId;
    String restaurantId;
    ResultDto resultDto;
    MultipartFile file;
}
