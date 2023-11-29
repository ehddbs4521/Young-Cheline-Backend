package YoungCheline.YoungCheline.controller;

import YoungCheline.YoungCheline.dto.AddMenuDto;
import YoungCheline.YoungCheline.dto.KakaoMapDto;
import YoungCheline.YoungCheline.dto.SurveyDto;
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
    public Object getMenu(@RequestParam("restaurantId") String restaurantId) throws JSONException {
        Map<String, String> menuName = new HashMap<>();
        String menu = evaluateServiceImpl.getMenu(restaurantId);
        menuName.put("menuName",menu);
        return menuName;
    }

    @PostMapping("/menu/add-menu")
    public ResponseEntity<Map<String, String>> addMenu(@RequestBody AddMenuDto addMenuDto) throws IOException {

        Map<String, String> menu = imageService.uploadMenu(addMenuDto.getFile(), bucket, addMenuDto.getRestaurantId(), addMenuDto.getMenuName());
        if (menu.get("menu") == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return ResponseEntity.ok().body(menu);
    }

    @PostMapping("/menu/survey/{menuId}")
    public ResponseEntity<Optional> evaluateMenu(@RequestBody SurveyDto surveyDto, Authentication authentication) throws IOException {


        evaluateServiceImpl.evaluateMenu(
                surveyDto.getRestaurantId(),
                surveyDto.getMenuId(),
                surveyDto.getResultDto().getFlavor(),
                surveyDto.getResultDto().getPrice(),
                surveyDto.getResultDto().getMood(),
                surveyDto.getResultDto().getCleanliness(),
                surveyDto.getResultDto().getPlating(),
                surveyDto.getResultDto().getService(),
                surveyDto.getFile(),
                authentication.getName(),
                LocalDateTime.now().toString(),
                bucket);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
