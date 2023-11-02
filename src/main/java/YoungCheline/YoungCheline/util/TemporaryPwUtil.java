package YoungCheline.YoungCheline.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TemporaryPwUtil {

    public String createTempPw() {
        String tempPw = UUID.randomUUID().toString().replace("-", "");
        tempPw = tempPw.substring(0,10);

        return tempPw;
    }

}
