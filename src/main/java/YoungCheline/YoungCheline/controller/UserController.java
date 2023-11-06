package YoungCheline.YoungCheline.controller;

import YoungCheline.YoungCheline.dto.EmailDto;
import YoungCheline.YoungCheline.dto.LoginDto;
import YoungCheline.YoungCheline.dto.RegisterDto;
import YoungCheline.YoungCheline.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDto registerDto) {
        userService.register(registerDto.getUserName(), registerDto.getPassword(), registerDto.getEmail());
        return ResponseEntity.ok().body("회원가입이 성공 했습니다.");
    }
    @PostMapping("/register/send-email")
    public ResponseEntity<String> sendEmail(@RequestBody EmailDto emailDto) throws MessagingException {
        log.info("email:{} ",emailDto.getEmail());

        return new ResponseEntity<>(userService.sendEmail(emailDto.getEmail()), HttpStatus.OK);

    }

    @PostMapping("/register/varify-email")
    public ResponseEntity<String> validateEmail(@RequestBody EmailDto emailDto) throws MessagingException {

        return new ResponseEntity<>(userService.validateEmail(emailDto.getEmail()), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        String token = userService.login(loginDto.getUserName(), loginDto.getPassword());
        return ResponseEntity.ok().body(token);
    }


    @PostMapping("/login/find-pw")
    public ResponseEntity<String> findPW(@RequestBody EmailDto emailDto) throws MessagingException {
        log.info("email:{}",emailDto.getEmail());
        String tempPw = userService.findPW(emailDto.getEmail());
        return ResponseEntity.ok().body(tempPw);
    }

}