package YoungCheline.YoungCheline.controller;

import YoungCheline.YoungCheline.dto.KakaoMapDto;
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
    public ResponseEntity<String> getMenu(@RequestBody HashMap<String, String> restaurantId) throws JSONException {
        String menu = evaluateServiceImpl.getMenu(restaurantId.get("restaurantId"));
        return ResponseEntity.ok().body(menu);
    }

    @PostMapping("/menu/add-menu")
    public ResponseEntity<Map<String, String>> addMenu(@RequestParam("file") MultipartFile file,
                                                       @RequestParam("restaurantId") String restaurantId,
                                                       @RequestParam("menuName") String menuName) throws IOException {

        Map<String, String> menu = imageService.uploadMenu(file, bucket, restaurantId, menuName);
        if (menu.get("menu") == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return ResponseEntity.ok().body(menu);
    }

    @PostMapping("/menu/survey/{menuId}")
    public ResponseEntity<Optional> evaluateMenu(@PathVariable Integer menuId,
                                                 @RequestParam("restaurantId") String restaurantId,
                                                 @RequestParam("taste") String taste,
                                                 @RequestParam("mood") List<String> mood,
                                                 @RequestParam("price") String price,
                                                 @RequestParam("cleaning") String cleaning,
                                                 @RequestParam("plating") String plating,
                                                 @RequestParam("service") String service,
                                                 @RequestParam("file") MultipartFile file,Authentication authentication) throws IOException {
        evaluateServiceImpl.evaluateMenu(
                restaurantId,
                menuId,
                taste,
                price,
                mood,
                cleaning,
                plating,
                service,
                file,
                authentication.getName(),
                LocalDateTime.now().toString(),
                bucket);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
