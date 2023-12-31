package YoungCheline.YoungCheline.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Getter
@Setter
public class ChangePwDto {

    @NotBlank
    private String currentPw;
    private String changepw;
}
