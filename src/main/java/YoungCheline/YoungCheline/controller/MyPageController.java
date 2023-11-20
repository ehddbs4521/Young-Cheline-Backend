package YoungCheline.YoungCheline.controller;

import YoungCheline.YoungCheline.error.MyPageError;
import YoungCheline.YoungCheline.service.ImageServiceImpl;
import YoungCheline.YoungCheline.service.MyPageServiceImpl;
import com.amazonaws.services.s3.AmazonS3Client;
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
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    final ImageServiceImpl imageServiceImpl;
    final MyPageServiceImpl myPageServiceImpl;

    @PostMapping("/change-pw")
    public ResponseEntity<Object> changePw(@RequestBody Map<String,String> changePwMap, Authentication authentication) {
        String changePw = changePwMap.get("changePw");
        String userName=authentication.getName();
        if (myPageServiceImpl.isSamePwEach(userName,changePw)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MyPageError("기존 비밀번호와 동일합니다"));
        }
        Map<String, String> pwMap = myPageServiceImpl.changePw(userName, changePw);
        return ResponseEntity.ok().body(pwMap);
    }
    @PostMapping("/profile-upload")
    public ResponseEntity<Map<String, URL>> uploadFile(@RequestParam("file") MultipartFile file,Authentication authentication) {
        try {
            Map<String, URL> profile = imageServiceImpl.uploadProfile(file, authentication.getName(), bucket);
            return ResponseEntity.ok().body(profile);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
