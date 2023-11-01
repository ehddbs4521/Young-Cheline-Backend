package YoungCheline.YoungCheline.controller;

import YoungCheline.YoungCheline.dto.AccountVerifyDto;
import YoungCheline.YoungCheline.dto.LoginDto;
import YoungCheline.YoungCheline.dto.RegisterDto;
import YoungCheline.YoungCheline.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register/varify-email")
    public ResponseEntity<Boolean> validateEmail(@RequestBody String email) {
        try {
            return new ResponseEntity<>(userService.checkEmailNotDuplicate(email), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        try {
            return new ResponseEntity<>(userService.register(registerDto), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/verify-account")
    public ResponseEntity<String> verifyAccount(@RequestBody AccountVerifyDto accountVerifyDto) {
        try {
            return new ResponseEntity<>(userService.verifyAccount(accountVerifyDto.getEmail(),
                    accountVerifyDto.getOtp()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/register/regenerate-otp")
    public ResponseEntity<String> regenerateOtp(@RequestBody String email) {
        try {
            return new ResponseEntity<>(userService.regenerateOtp(email), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        try {
            return new ResponseEntity<>(userService.login(loginDto), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

}