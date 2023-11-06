package YoungCheline.YoungCheline.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AppException extends RuntimeException{

    private ErrorCode errorcode;
    private String message;
}
