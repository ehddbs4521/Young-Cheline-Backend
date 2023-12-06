package YoungCheline.YoungCheline.controller;

import YoungCheline.YoungCheline.dto.ChangePwDto;
import YoungCheline.YoungCheline.dto.RestaurantEvaluateDto;
import YoungCheline.YoungCheline.dto.TopTenDto;
import YoungCheline.YoungCheline.service.ImageServiceImpl;
import YoungCheline.YoungCheline.service.MyPageServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    final ImageServiceImpl imageServiceImpl;
    final MyPageServiceImpl myPageServiceImpl;

    @PostMapping("/change-pw")
    public ResponseEntity<Object> changePw(@RequestBody ChangePwDto changePwDto, Authentication authentication) {
        Map<String, String> error = new HashMap<>();

        String changePw = changePwDto.getChangepw();
        String userName = authentication.getName();
        if (myPageServiceImpl.isSamePwEach(userName, changePw)) {
            error.put("error","기존 비밀번호와 동일");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
        Map<String, String> pwMap = myPageServiceImpl.changePw(userName, changePw);
        return ResponseEntity.ok().body(pwMap);
    }

    @PostMapping("/profile-upload")
    public ResponseEntity<Map<String, URL>> uploadFile(@RequestParam("file") MultipartFile file, Authentication authentication) {
        try {
            Map<String, URL> profile = imageServiceImpl.uploadProfile(file, authentication.getName(), bucket);
            return ResponseEntity.ok().body(profile);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/evaluate-list")
    public Object showEvaluateList(@RequestParam("size") Integer size,@RequestParam(defaultValue = "0") Integer id, Authentication authentication) {
        RestaurantEvaluateDto[] restaurantEvaluateDtos = myPageServiceImpl.showEvaluateList(size, id, authentication.getName());
        return restaurantEvaluateDtos;
    }

    @GetMapping("/top10-list")
    public Object showTop10List(Authentication authentication) {
        TopTenDto[] topTenDtos = myPageServiceImpl.showTop10List(authentication.getName());
        return topTenDtos;
    }

    @PostMapping("/top10-list")
    public ResponseEntity<Object> sendTop10List(@RequestBody TopTenDto[] topTenDto, Authentication authentication) {

        Map<String, String> error = new HashMap<>();
        error.put("error", "같은 메뉴 중복");
        boolean result = myPageServiceImpl.sendTop10List(topTenDto, authentication.getName());
        if (result == true) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @PostMapping("/delete-top10-list")
    public ResponseEntity<Object> deleteTop10List(@RequestBody TopTenDto topTenDto, Authentication authentication) {

        Map<String, String> error = new HashMap<>();
        myPageServiceImpl.deleteTop10List(topTenDto, authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
    @PostMapping("/withdraw")
    public ResponseEntity<Object> withdraw(Authentication authentication) {
        myPageServiceImpl.withdraw(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
