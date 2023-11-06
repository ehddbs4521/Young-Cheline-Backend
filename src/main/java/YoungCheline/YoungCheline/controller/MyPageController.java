package YoungCheline.YoungCheline.controller;

import YoungCheline.YoungCheline.dto.LoginDto;
import YoungCheline.YoungCheline.service.MyPageService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/mypage")
public class MyPageController {

    @Autowired
    MyPageService myPageService;

    /*@PostMapping("/change-pw")
    public ResponseEntity<String> changePw(@Valid @RequestBody LoginDto loginDto, Errors error) {
        if (error.hasErrors()) {
            log.info("error:{}", error);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            try {
                return new ResponseEntity<>(myPageService.changePw(loginDto), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
    }*/
}
