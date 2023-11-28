package YoungCheline.YoungCheline.controller;

import YoungCheline.YoungCheline.dto.RestaurantEvaluateDto;
import YoungCheline.YoungCheline.entity.RestaurantEvaluate;
import YoungCheline.YoungCheline.service.MainPageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
public class MainPageController {

    private final MainPageServiceImpl mainPageService;

    @GetMapping
    public ResponseEntity<RestaurantEvaluateDto[]> getHome(@RequestParam(defaultValue = "0") Integer id, @RequestParam("size") Integer size) {
        RestaurantEvaluateDto[] restaurantEvaluateDto = mainPageService.showEvaluateBox(size, id);
        return ResponseEntity.ok().body(restaurantEvaluateDto);
    }

    @GetMapping("/search")
    public ResponseEntity<RestaurantEvaluateDto[]> getSearch(@RequestParam("keyword") String menuName, @RequestParam(defaultValue = "0") Integer id, @RequestParam("size") Integer size) {
        long total = mainPageService.totalDataCount(menuName);
        RestaurantEvaluateDto[] restaurantEvaluateDto = mainPageService.showEvaluateBoxByKeyWord(menuName, size, id, total);
        return ResponseEntity.ok().body(restaurantEvaluateDto);
    }

    @GetMapping("/search/filter")
    public ResponseEntity<RestaurantEvaluateDto[]> getSearchFilter(@RequestParam("keyword") String menuName, @RequestParam("size") Integer size, @RequestParam(defaultValue = "0") Integer id
            , @RequestParam("flavor") List<String> flavor
            , @RequestParam("mood") List<String> mood
            , @RequestParam("plating") List<String> plating
            , @RequestParam("cleaning") List<String> cleaning
            , @RequestParam("service") List<String> service
    ) {
        List<RestaurantEvaluate> menu = mainPageService.init(menuName, flavor, mood, plating, cleaning, service);
        RestaurantEvaluateDto[] restaurantEvaluateDto = mainPageService.showEvaluateBoxByKeyWordFilter(size, id, menu);
        return ResponseEntity.ok().body(restaurantEvaluateDto);
    }

}
