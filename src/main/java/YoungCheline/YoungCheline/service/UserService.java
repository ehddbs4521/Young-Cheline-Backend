package YoungCheline.YoungCheline.service;

import YoungCheline.YoungCheline.dto.LoginDto;
import YoungCheline.YoungCheline.dto.RegisterDto;
import YoungCheline.YoungCheline.entity.User;
import YoungCheline.YoungCheline.repository.UserRepository;
import YoungCheline.YoungCheline.util.EmailUtil;
import YoungCheline.YoungCheline.util.OtpUtil;
import YoungCheline.YoungCheline.util.TemporaryPwUtil;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    private OtpUtil otpUtil;
    @Autowired
    private EmailUtil emailUtil;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private TemporaryPwUtil temporaryPwUtil;

    public String register(RegisterDto registerDto) {
        String otp = otpUtil.generateOtp();
        try {
            emailUtil.sendOtpEmail(registerDto.getEmail(), otp);
        } catch (MessagingException e) {
            throw new RuntimeException("인증 링크 전송에 문제가 생겼습니다. 다시 보내주세요");
        }
        User user = new User();
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(registerDto.getPassword());
        user.setOtp(otp);
        user.setOtpGeneratedTime(LocalDateTime.now());
        userRepository.save(user);
        return "이름,이메일,비밀번호 입력 완료";
    }

    public boolean checkEmailNotDuplicate(String email) {
        if (!userRepository.existsByEmail(email)) {
            return true;
        }
        return false;
    }

    public String varifyAccount(String email, String otp) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("이메일을 찾을 수 없습니다: " + email));
        if (user.getOtp().equals(otp) && Duration.between(user.getOtpGeneratedTime(),
                LocalDateTime.now()).getSeconds() < (1 * 60 * 60 * 10)) {
            user.setActive(true);
            user.setToken(tokenService.generateToken(email));
            userRepository.save(user);
            return "로그인 할 수 있습니다";
        }
        return "인증링크를 재발급 해주세요";
    }

    public String regenerateOtp(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("이메일을 찾을 수 없습니다: " + email));
        String otp = otpUtil.generateOtp();
        String token = tokenService.generateToken(email);
        try {
            emailUtil.sendOtpEmail(email, otp);
        } catch (MessagingException e) {
            throw new RuntimeException("인증 링크 전송에 문제가 생겼습니다. 다시 보내주세요");
        }
        user.setOtp(otp);
        user.setOtpGeneratedTime(LocalDateTime.now());
        userRepository.save(user);
        return "이메일에 링크를 전송했습니다. 10분 안에 인증을 해주세요";
    }

    public String login(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(
                        () -> new RuntimeException("이메일을 찾을 수 없습니다: " + loginDto.getEmail()));
        if (!loginDto.getPassword().equals(user.getPassword()) || !loginDto.getPassword().equals(user.getTempPw())) {
            return "패스워드가 틀립니다";
        } else if (!user.isActive()) {
            return "인증이 안된 회원입니다";
        }
        return "성공적으로 로그인했습니다";
    }

    public String findPW(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new RuntimeException("이메일을 찾을 수 없습니다: " + email)
                );
        return user.getPassword();
    }

    public String generateTempPw(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new RuntimeException("이메일을 찾을 수 없습니다: " + email)
                );

        String tempPw = temporaryPwUtil.createTempPw();
        try {
            emailUtil.sendTempPwEmail(email, tempPw);
            user.setTempPw(tempPw);
            userRepository.save(user);
        } catch (MessagingException e) {
            throw new RuntimeException("임시 비밀번호 전송에 문제가 생겼습니다. 다시 보내주세요");
        }
        return "임시 비밀번호 전송 완료";
    }
}
