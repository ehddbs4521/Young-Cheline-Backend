package YoungCheline.YoungCheline.service;

import YoungCheline.YoungCheline.dto.RecommendDto;
import YoungCheline.YoungCheline.dto.ResultDto;
import YoungCheline.YoungCheline.entity.HotMenu;
import YoungCheline.YoungCheline.entity.RestaurantEvaluate;
import YoungCheline.YoungCheline.entity.SteadyMenu;
import YoungCheline.YoungCheline.repository.RestaurantEvaluateRepository;
import YoungCheline.YoungCheline.repository.SteadyMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SteadyMenuServiceImpl implements SteadyMenuService {
    private final RestaurantEvaluateRepository restaurantEvaluateRepository;
    private final SteadyMenuRepository steadyMenuRepository;

    public RecommendDto[] getSteadyMenu() {
        LocalDate start=LocalDate.now();
        LocalDate end = start.minusMonths(1);
        List<SteadyMenu> content = steadyMenuRepository.findBySteadyMenuKey_TimeBetween(start,end).stream().collect(Collectors.toList());
        RecommendDto[] recommendDto = new RecommendDto[content.size()];
        ResultDto resultDto = new ResultDto();

        for (int i = 0; i < content.size(); i++) {
            recommendDto[i] = new RecommendDto();
            recommendDto[i].setMenuId(content.get(i).getSteadyMenuKey().getMenuId());
            Integer menuId = content.get(i).getSteadyMenuKey().getMenuId();
            recommendDto[i].setMenuId(menuId);

            RestaurantEvaluate restaurantEvaluate = restaurantEvaluateRepository.findByMenuId(menuId).get();
            recommendDto[i].setRestaurantId(restaurantEvaluate.getRestaurantId());
            recommendDto[i].setMenuName(restaurantEvaluate.getMenuName());
            List<String> mood = checkMood(restaurantEvaluate.getCouple(), restaurantEvaluate.getFamily(), restaurantEvaluate.getSolo(), restaurantEvaluate.getFriend(), restaurantEvaluate.getDrink());

            resultDto.setFlavor(restaurantEvaluate.getFlavor());
            resultDto.setPrice(restaurantEvaluate.getPrice());
            resultDto.setCleanliness(restaurantEvaluate.getCleaning());
            resultDto.setPlating(restaurantEvaluate.getPlating());
            resultDto.setService(restaurantEvaluate.getService());
            resultDto.setMood(mood);

            recommendDto[i].setEvaluate(resultDto);
            recommendDto[i].setUrl(restaurantEvaluate.getUrl());
        }
        return recommendDto;
    }
    private List<String> checkMood(String couple, String family, String solo, String friend, String drink) {
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
