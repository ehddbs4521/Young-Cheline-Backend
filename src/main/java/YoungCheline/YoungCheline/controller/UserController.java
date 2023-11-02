package YoungCheline.YoungCheline.controller;

import YoungCheline.YoungCheline.dto.AccountVerifyDto;
import YoungCheline.YoungCheline.dto.LoginDto;
import YoungCheline.YoungCheline.dto.RegisterDto;
import YoungCheline.YoungCheline.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
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
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDto registerDto, Errors errors) {
        if (errors.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            try {
                if (userService.checkEmailNotDuplicate(registerDto.getEmail())) {
                    return new ResponseEntity<>(userService.register(registerDto), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }


    }

    @PostMapping("/varify-account")
    public ResponseEntity<String> varifyAccount(@RequestBody AccountVerifyDto accountVerifyDto) {
        try {
            return new ResponseEntity<>(userService.varifyAccount(accountVerifyDto.getEmail(), accountVerifyDto.getOtp()), HttpStatus.OK);
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

    @PostMapping("/login/find-pw")
    public ResponseEntity<String> findPw(@RequestBody LoginDto loginDto) {
        try {
            return new ResponseEntity<>(userService.findPW(loginDto.getEmail()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/login/find-pw/generate-pw")
    public ResponseEntity<String> generateTempPw(@RequestBody LoginDto loginDto) {
        try {
            return new ResponseEntity<>(userService.findPW(loginDto.getEmail()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }



}