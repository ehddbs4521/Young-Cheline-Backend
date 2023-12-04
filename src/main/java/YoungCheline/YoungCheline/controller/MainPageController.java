package YoungCheline.YoungCheline.controller;

import YoungCheline.YoungCheline.dto.RestaurantEvaluateDto;
import YoungCheline.YoungCheline.entity.RestaurantEvaluate;
import YoungCheline.YoungCheline.service.MainPageServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/home")
public class MainPageController {

    private final MainPageServiceImpl mainPageService;

    @GetMapping
    public Object getHome(@RequestParam(defaultValue = "0") Integer id, @RequestParam("size") Integer size) {
        RestaurantEvaluateDto[] restaurantEvaluateDto = mainPageService.showEvaluateBox(size, id);

        return restaurantEvaluateDto;
    }

    @GetMapping("/search")
    public Object getSearch(@RequestParam("keyword") String menuName, @RequestParam(defaultValue = "0") Integer id, @RequestParam("size") Integer size) {
        long total = mainPageService.totalDataCount(menuName);

        RestaurantEvaluateDto[] restaurantEvaluateDto = mainPageService.showEvaluateBoxByKeyWord(menuName, size, id, total);
        return restaurantEvaluateDto;
    }

    @GetMapping("/search/filter")
    public RestaurantEvaluateDto[] getSearchFilter(@RequestParam("keyword") String menuName, @RequestParam("size") Integer size, @RequestParam(defaultValue = "0") Integer id
            , @RequestParam(defaultValue = "") List<String> flavor
            , @RequestParam(defaultValue = "") List<String> mood
            , @RequestParam(defaultValue = "") List<String> plating
            , @RequestParam(defaultValue = "") List<String> cleanliness
            , @RequestParam(defaultValue = "") List<String> service
    ) {
        List<RestaurantEvaluate> menu = mainPageService.init(menuName, flavor, mood, plating, cleanliness, service);
        RestaurantEvaluateDto[] restaurantEvaluateDto = mainPageService.showEvaluateBoxByKeyWordFilter(size, id, menu);

        return restaurantEvaluateDto;
    }

}
