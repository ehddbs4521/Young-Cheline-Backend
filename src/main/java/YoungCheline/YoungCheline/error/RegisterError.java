package YoungCheline.YoungCheline.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@RequiredArgsConstructor
public class RegisterError {

    private String message;

    public RegisterError(String message) {
        this.message = message;
    }

}
