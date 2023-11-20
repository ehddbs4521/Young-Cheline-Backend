package YoungCheline.YoungCheline.controller;

import YoungCheline.YoungCheline.dto.EmailDto;
import YoungCheline.YoungCheline.dto.JwtDto;
import YoungCheline.YoungCheline.dto.LoginDto;
import YoungCheline.YoungCheline.dto.RegisterDto;
import YoungCheline.YoungCheline.entity.User;
import YoungCheline.YoungCheline.error.RegisterError;
import YoungCheline.YoungCheline.error.UserError;
import YoungCheline.YoungCheline.service.UserServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    final private UserServiceImpl userServiceImpl;
    final private JwtDto jwtDto;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterDto registerDto) {
        if (userServiceImpl.validateDuplicateId(registerDto.getUserName())) {
            if (userServiceImpl.register(registerDto.getUserName(), registerDto.getPassword(), registerDto.getEmail())) {
                return ResponseEntity.ok().body(registerDto);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RegisterError("회원가입이 실패하였습니다"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RegisterError("아이디가 이미 존재합니다"));
    }

    @PostMapping("/register/send-email")
    public ResponseEntity<Object> sendEmail(@RequestBody EmailDto emailDto) throws MessagingException {
        if (userServiceImpl.sendEmail(emailDto.getEmail())) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RegisterError("입력하신 이메일로 회원가입이 되있습니다."));
    }

    @PostMapping("/register/varify-email")
    public ResponseEntity<Object> validateEmail(@RequestBody EmailDto emailDto) throws MessagingException {
        if (userServiceImpl.validateEmail(emailDto.getEmail())) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RegisterError("이메일 인증 링크 전송 실패"));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginDto loginDto) {
        if (userServiceImpl.login(loginDto.getUserName(), loginDto.getPassword())) {
            return ResponseEntity.ok().body(jwtDto);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RegisterError("로그인 실패"));
    }

    @PostMapping("/login/find-id")
    public ResponseEntity<Object> findId(@RequestBody EmailDto emailDto) {
        if (userServiceImpl.findID(emailDto.getEmail())) {
            User user = userServiceImpl.getUserByEmail(emailDto.getEmail());
            return ResponseEntity.ok().body(user.getUserName());
        }
        return ResponseEntity.badRequest().body(new UserError("입력하신 이메일로 아이디를 찾을 수 없습니다"));
    }

    @PostMapping("/login/find-pw")
    public ResponseEntity<Object> findPW(@RequestBody EmailDto emailDto) throws MessagingException {
        if (userServiceImpl.findPwByEmail(emailDto.getEmail())) {

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RegisterError("가입하신 이메일을 다시 입력해주세요."));

    }

}