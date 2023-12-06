package YoungCheline.YoungCheline.service;

import YoungCheline.YoungCheline.dto.RestaurantEvaluateDto;
import YoungCheline.YoungCheline.entity.RestaurantEvaluate;

import java.util.List;

public interface MainPageService {
    RestaurantEvaluateDto[] showEvaluateBox(Integer size, Integer number);
    List<String> checkMood(String couple, String family, String solo, String friend, String drink);
    long totalDataCount(String menuName);
    RestaurantEvaluateDto[] showEvaluateBoxByKeyWord(String menuName, Integer size, Integer id, long total);
    RestaurantEvaluateDto[] showEvaluateBoxByKeyWordFilter(Integer size, Integer id, List<RestaurantEvaluate> content);
    List<RestaurantEvaluate> init(String menuName, List<String> flavor, List<String> mood, List<String> plating, List<String> cleaning, List<String> service);
}
