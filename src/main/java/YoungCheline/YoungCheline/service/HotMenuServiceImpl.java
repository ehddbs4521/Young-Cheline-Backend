package YoungCheline.YoungCheline.service;

import YoungCheline.YoungCheline.dto.RecommendDto;
import YoungCheline.YoungCheline.dto.ResultDto;
import YoungCheline.YoungCheline.entity.HotMenu;
import YoungCheline.YoungCheline.entity.Recommend;
import YoungCheline.YoungCheline.entity.RestaurantEvaluate;
import YoungCheline.YoungCheline.repository.HotMenuRepository;
import YoungCheline.YoungCheline.repository.RestaurantEvaluateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class HotMenuServiceImpl implements HotMenuService {

    private final RestaurantEvaluateRepository restaurantEvaluateRepository;
    private final HotMenuRepository hotMenuRepository;
    public RecommendDto[] getHotMenu() {
        LocalDate start=LocalDate.now();
        LocalDate end = start.minusDays(1);
        List<HotMenu> content = hotMenuRepository.findAllByHotMenuKey_TimeBetween(end,start);
        RecommendDto[] recommendDto = new RecommendDto[content.size()];

        for (int i = 0; i < content.size(); i++) {
            ResultDto resultDto = new ResultDto();
            recommendDto[i] = new RecommendDto();
            recommendDto[i].setMenuId(content.get(i).getHotMenuKey().getMenuId());
            Integer menuId = content.get(i).getHotMenuKey().getMenuId();
            recommendDto[i].setMenuId(menuId);

            RestaurantEvaluate restaurantEvaluate = restaurantEvaluateRepository.findByMenuId(menuId).get();
            recommendDto[i].setRestaurantId(restaurantEvaluate.getRestaurantId());
            recommendDto[i].setMenuName(restaurantEvaluate.getMenuName());
            resultDto.setFlavor(restaurantEvaluate.getFlavor());
            List<String> mood = checkMood(restaurantEvaluate.getCouple(), restaurantEvaluate.getFamily(), restaurantEvaluate.getSolo(), restaurantEvaluate.getFriend(), restaurantEvaluate.getDrink());
            resultDto.setMood(mood);
            resultDto.setPrice(restaurantEvaluate.getPrice());
            resultDto.setPlating(restaurantEvaluate.getPlating());
            resultDto.setService(restaurantEvaluate.getService());

            recommendDto[i].setEvaluate(resultDto);
            if (restaurantEvaluate.getUrl() == null) {
                recommendDto[i].setUrl(null);
            } else {
                recommendDto[i].setUrl(restaurantEvaluate.getUrl());
            }
        }
        return recommendDto;
    }
    public List<String> checkMood(String couple, String family, String solo, String friend, String drink) {
        List<String> mood = new ArrayList<>();
        if (couple != null) {
            mood.add(couple);
        }
        if (family != null) {
            mood.add(family);
        }
        if (solo != null) {
            mood.add(solo);
        }
        if (drink != null) {
            mood.add(drink);
        }
        if (friend != null) {
            mood.add(friend);
        }

        return mood;
    }
}
