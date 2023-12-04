package YoungCheline.YoungCheline.controller;

import YoungCheline.YoungCheline.dto.*;
import YoungCheline.YoungCheline.entity.User;
import YoungCheline.YoungCheline.service.UserServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    final private UserServiceImpl userServiceImpl;
    final private JwtDto jwtDto;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterDto registerDto) {
        Map<String, String> error = new HashMap<>();

        if (userServiceImpl.validateDuplicateId(registerDto.getUserName())) {
            if (userServiceImpl.register(registerDto.getUserName(), registerDto.getPassword(), registerDto.getEmail())) {
                return ResponseEntity.ok().body(registerDto);
            }
            error.put("error", "회원가입 실패");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
        error.put("error", "아이디 존재");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @PostMapping("/register/send-email")
    public ResponseEntity<Object> sendEmail(@RequestBody EmailDto emailDto) throws MessagingException {
        System.out.println(emailDto.getEmail());
        Map<String, String> error = new HashMap<>();
        Map<String, String> email = new HashMap<>();
        if (userServiceImpl.sendEmail(emailDto.getEmail())) {
            email.put("email", emailDto.getEmail());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        error.put("error", "이메일 존재");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @PostMapping("/register/verify-email")
    public ResponseEntity<Object> validateEmail(@RequestBody EmailDto emailDto) throws MessagingException {
        Map<String, String> error = new HashMap<>();

        if (userServiceImpl.validateEmail(emailDto.getEmail())) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        error.put("error", "이메일 전송 실패");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginDto loginDto) {
        Map<String, String> error = new HashMap<>();

        if (userServiceImpl.login(loginDto.getUserName(), loginDto.getPassword())) {
            return ResponseEntity.ok().body(jwtDto);
        }
        error.put("error", "로그인 실패");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @PostMapping("/login/find-id")
    public ResponseEntity<Object> findId(@RequestBody EmailDto emailDto) {
        Map<String, String> error = new HashMap<>();

        if (userServiceImpl.findID(emailDto.getEmail())) {
            User user = userServiceImpl.getUserByEmail(emailDto.getEmail());
            return ResponseEntity.ok().body(user.getUserName());
        }
        error.put("error", "이메일 존재하지 않음");

        return ResponseEntity.badRequest().body(error);
    }

    @PostMapping("/login/find-pw")
    public ResponseEntity<Object> findPW(@RequestBody EmailDto emailDto) throws MessagingException {
        Map<String, String> error = new HashMap<>();

        if (userServiceImpl.findPwByEmail(emailDto.getEmail())) {

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        error.put("error", "이메일 존재하지 않음");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);

    }

    @GetMapping("/me")
    public Object getProfile(Authentication authentication) {
        ProfileDto profile = userServiceImpl.getProfile(authentication.getName());

        return profile;

    }

}