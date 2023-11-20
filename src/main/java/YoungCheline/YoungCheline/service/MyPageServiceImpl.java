package YoungCheline.YoungCheline.service;

import YoungCheline.YoungCheline.dto.LoginDto;
import YoungCheline.YoungCheline.entity.User;
import YoungCheline.YoungCheline.exception.AppException;
import YoungCheline.YoungCheline.exception.ErrorCode;
import YoungCheline.YoungCheline.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public Map<String,String> changePw(String userName, String changePw) {
        Map<String, String> pwMap = new HashMap<>();
        User user = userRepository.findByUserName(userName)
                .orElseThrow(
                        () -> new AppException(ErrorCode.BAD_REQUEST,"사용자를 찾을 수 없습니다.")
                );

        user.setPassword(encoder.encode(changePw));
        userRepository.save(user);
        pwMap.put("password", changePw);
        return pwMap;

    }

    public boolean isSamePwEach(String userName,String changePw) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(
                        () -> new AppException(ErrorCode.BAD_REQUEST,"사용자를 찾을 수 없습니다.")
                );
        if (encoder.matches(changePw, user.getPassword())) {
            return true;
        }
        return false;
    }
}
