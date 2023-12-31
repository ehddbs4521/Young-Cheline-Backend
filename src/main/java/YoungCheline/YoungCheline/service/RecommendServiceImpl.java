package YoungCheline.YoungCheline.service;

import YoungCheline.YoungCheline.dto.RecommendDto;
import YoungCheline.YoungCheline.dto.ResultDto;
import YoungCheline.YoungCheline.entity.Recommend;
import YoungCheline.YoungCheline.entity.RestaurantEvaluate;
import YoungCheline.YoungCheline.repository.EvaluateRepository;
import YoungCheline.YoungCheline.repository.RecommendRepository;
import YoungCheline.YoungCheline.repository.RestaurantEvaluateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendServiceImpl implements RecommendService {

    private final EvaluateRepository evaluateRepository;
    private final RecommendRepository recommendRepository;
    private final RestaurantEvaluateRepository restaurantEvaluateRepository;
    public boolean checkRecommend(String userName) {
        int count = evaluateRepository.findAllByKey_UserName(userName).size();
        System.out.println(count);
        if (count < 5) {
            return false;
        }
        return true;
    }

    public RecommendDto[] getRecommend(String userName) {
        LocalDate start=LocalDate.now();
        LocalDate end = start.minusDays(7);
        List<Recommend> content = recommendRepository.findAllByRecommendKey_UserNameAndRecommendKey_TimeBetween(userName,end,start);

        RecommendDto[] recommendDto = new RecommendDto[content.size()];

        for (int i = 0; i < content.size(); i++) {
            ResultDto resultDto = new ResultDto();
            recommendDto[i] = new RecommendDto();
            recommendDto[i].setUserName(userName);
            recommendDto[i].setMenuId(content.get(i).getRecommendKey().getMenuId());
            Integer menuId = content.get(i).getRecommendKey().getMenuId();
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

