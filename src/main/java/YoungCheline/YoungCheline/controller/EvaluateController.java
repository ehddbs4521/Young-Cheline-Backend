package YoungCheline.YoungCheline.controller;

import YoungCheline.YoungCheline.dto.*;
import YoungCheline.YoungCheline.service.EvaluateServiceImpl;
import YoungCheline.YoungCheline.service.ImageServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:3000")
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
    public Object getMenu(@RequestParam("restaurantId") String restaurantId)  {
        GetMenuDto[] menu = evaluateServiceImpl.getMenu(restaurantId);

        return menu;
    }

    @PostMapping("/menu/add-menu")
    public ResponseEntity<AddMenuUrlDto> addMenu(@RequestParam("file") MultipartFile file,@ModelAttribute AddMenuDto addMenuDto) throws IOException {
        System.out.println(file.getOriginalFilename().toString());
        AddMenuUrlDto menu = imageService.uploadMenu(file, bucket, addMenuDto.getRestaurantId(), addMenuDto.getMenuName());
        if (menu == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return ResponseEntity.ok().body(menu);
    }

    @PostMapping("/menu/survey")
    public ResponseEntity<Optional> evaluateMenu(@RequestParam("file") MultipartFile file,@ModelAttribute SurveyDto surveyDto, Authentication authentication) throws IOException {

        evaluateServiceImpl.evaluateMenu(
                surveyDto.getRestaurantId(),
                surveyDto.getMenuId(),
                surveyDto.getFlavor(),
                surveyDto.getPrice(),
                surveyDto.getMood(),
                surveyDto.getCleanliness(),
                surveyDto.getPlating(),
                surveyDto.getService(),
                file,
                authentication.getName(),
                LocalDateTime.now(),
                bucket);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
