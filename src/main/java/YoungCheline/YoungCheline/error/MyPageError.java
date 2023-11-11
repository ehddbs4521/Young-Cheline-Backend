package YoungCheline.YoungCheline.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@RequiredArgsConstructor
public class MyPageError {

    private String message;
    public MyPageError(String message) {
        this.message = message;
    }}
