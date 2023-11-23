package YoungCheline.YoungCheline.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EvaluateDto {
    private String restaurantId;
    private String taste;
    private List<String> mood;
    private String price;
    private String cleaning;
    private String plating;
    private String service;
}
