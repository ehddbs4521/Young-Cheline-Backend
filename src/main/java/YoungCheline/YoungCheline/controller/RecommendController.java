package YoungCheline.YoungCheline.controller;

import YoungCheline.YoungCheline.dto.RecommendDto;
import YoungCheline.YoungCheline.dto.RecommendationResponseDto;
import YoungCheline.YoungCheline.service.HotMenuServiceImpl;
import YoungCheline.YoungCheline.service.RecommendServiceImpl;
import YoungCheline.YoungCheline.service.SteadyMenuServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/recommend")
public class RecommendController {
    final RecommendServiceImpl recommendService;
    final HotMenuServiceImpl hotMenuService;
    final SteadyMenuServiceImpl steadyMenuService;
    @GetMapping
    public Object getRecommend(Authentication authentication) {
        RecommendationResponseDto recommendationResponseDto = new RecommendationResponseDto();
        Map<String, String> error = new HashMap<>();
        error.put("error", "평가 횟수 5회 미만");

        if (recommendService.checkRecommend(authentication.getName())) {
            RecommendDto[] recommend = recommendService.getRecommend(authentication.getName());
            for (int i = 0; i < recommend.length; i++) {
                System.out.println(recommend[i].getMenuId());
                System.out.println(recommend[i].getMenuName());
            }
            RecommendDto[] hotMenu = hotMenuService.getHotMenu();
            RecommendDto[] steadyMenu = steadyMenuService.getSteadyMenu();
            recommendationResponseDto.setRecommend(recommend);
            recommendationResponseDto.setHotMenu(hotMenu);
            recommendationResponseDto.setSteadyMenu(steadyMenu);

            return recommendationResponseDto;
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
