package YoungCheline.YoungCheline.controller;

import YoungCheline.YoungCheline.dto.AddMenuDto;
import YoungCheline.YoungCheline.dto.KakaoMapDto;
import YoungCheline.YoungCheline.dto.MenuDto;
import YoungCheline.YoungCheline.service.EvaluateServiceImpl;
import YoungCheline.YoungCheline.service.ImageServiceImpl;
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
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/evaluate")
public class EvaluateController {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    final EvaluateServiceImpl evaluateServiceImpl;
    final ImageServiceImpl imageService;

    @PostMapping("/find-restaurant")
    public ResponseEntity<Optional> findRestaurant(@RequestBody KakaoMapDto kakaoMapDto) {
        KakaoMapDto kakaoMap = evaluateServiceImpl.saveRestaurant(kakaoMapDto);
        if (kakaoMap != null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/menu")
    public ResponseEntity<MenuDto> getMenu(@RequestBody HashMap<String, String> restaurantId) {
        MenuDto menuDto = evaluateServiceImpl.getMenu(restaurantId.get("restaurantId"));
        return ResponseEntity.ok().body(menuDto);
    }

    @PostMapping("/menu/add-menu")
    public ResponseEntity<Map<String, String>> addMenu(@RequestParam("file") MultipartFile file,
                                                       @RequestParam("id") String id,
                                                       @RequestParam("menuId") String menuName) throws IOException {

        Map<String, String> menu = imageService.uploadMenu(file, bucket, id, menuName);
        if (menu.get("menu") == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return ResponseEntity.ok().body(menu);
    }


}
