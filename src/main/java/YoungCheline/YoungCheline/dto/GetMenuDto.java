package YoungCheline.YoungCheline.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetMenuDto {
    Integer menuId;
    String menuName;
    String url;
}
