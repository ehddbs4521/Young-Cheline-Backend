package YoungCheline.YoungCheline.controller;

import YoungCheline.YoungCheline.service.MyPageServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/mypage")
public class MyPageController {

    @Autowired
    MyPageServiceImpl myPageServiceImpl;

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
