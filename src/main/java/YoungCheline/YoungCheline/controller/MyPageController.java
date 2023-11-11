package YoungCheline.YoungCheline.controller;

import YoungCheline.YoungCheline.dto.ChangePwDto;
import YoungCheline.YoungCheline.dto.LoginDto;
import YoungCheline.YoungCheline.error.MyPageError;
import YoungCheline.YoungCheline.service.MyPageServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {

    final MyPageServiceImpl myPageServiceImpl;

    @PostMapping("/change-pw")
    public ResponseEntity<Object> changePw(@RequestBody ChangePwDto changePwDto) {
        if (changePwDto.getChangepw().equals(changePwDto.getCurrentpw())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MyPageError("기존 비밀번호와 동일합니다"));
        }
        else if (myPageServiceImpl.changePw(changePwDto.getUserName(),changePwDto.getCurrentpw(), changePwDto.getChangepw())) {
            return ResponseEntity.ok().body(changePwDto.getChangepw());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MyPageError("비밀번호 변경 실패"));
    }

}
