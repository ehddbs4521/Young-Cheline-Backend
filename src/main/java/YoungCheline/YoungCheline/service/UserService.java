package YoungCheline.YoungCheline.service;

import YoungCheline.YoungCheline.config.EncoderConfig;
import YoungCheline.YoungCheline.dto.LoginDto;
import YoungCheline.YoungCheline.entity.User;
import YoungCheline.YoungCheline.exception.AppException;
import YoungCheline.YoungCheline.exception.ErrorCode;
import YoungCheline.YoungCheline.repository.UserRepository;
import YoungCheline.YoungCheline.util.EmailUtil;
import YoungCheline.YoungCheline.util.JwtTokenUtil;
import YoungCheline.YoungCheline.util.TemporaryPwUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final EmailUtil emailUtil;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final TemporaryPwUtil temporaryPwUtil;
    @Value("${secretkey}")
    private String secretKey;
    private Long expiredMs=1000*60*60*24*50L;


    public String register(String userName,String password,String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("이메일을 찾을 수 없습니다: " + email));
        System.out.println(user);
        if (user.getUserName()==null||user.getUserName()!=userName) {
            user.setUserName(userName);
            user.setPassword(encoder.encode(password));
            user.setEmail(email);

            userRepository.save(user);
        } else if (user.getUserName().equals(userName)) {
            throw new AppException(ErrorCode.USERNAME_DUPLICATED,userName+"는 이미 존재합니다");
        }

        return "회원가입 성공";
    }

    public String sendEmail(String email) throws MessagingException {
        if (!userRepository.existsByEmail(email)) {
            emailUtil.sendEmail(email);
            User user = new User();
            user.setEmail(email);
            user.setActive(false);
            user.setTime(LocalDateTime.now());
            userRepository.save(user);
            return "SUCCESS";
        }
        return "FAIL";
    }

    public String validateEmail(String email) throws MessagingException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("이메일을 찾을 수 없습니다: " + email));
        if (Duration.between(user.getTime(),
                LocalDateTime.now()).getSeconds() < (1 * 60 * 60 * 10)) {
            user.setActive(true);
            userRepository.save(user);
            return "이메일을 인증했습니다.";
        }
        return "인증링크를 재발급 해주세요";
    }

    public String login(String userName, String password) {

        User user=userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, userName + "이 없습니다"));
        if (!encoder.matches(password, user.getPassword()) && !encoder.matches(password, user.getTempPw())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD, "패스워드를 잘못 입력했습니다.");
        }

        String token = JwtTokenUtil.createJwt(user.getUserName(), secretKey, expiredMs);
        return token;
    }

    public String findPW(String email) throws MessagingException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new RuntimeException("이메일을 찾을 수 없습니다: " + email)
                );
        String tempPw=temporaryPwUtil.createTempPw();
        user.setTempPw(encoder.encode(tempPw));
        userRepository.save(user);
        emailUtil.sendTempPwEmail(email,tempPw);

        return "임시 비밀번호를 발급했습니다.";
    }
}
