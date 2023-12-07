package YoungCheline.YoungCheline.service;

import YoungCheline.YoungCheline.dto.DetailDto;
import YoungCheline.YoungCheline.dto.ResultDto;
import YoungCheline.YoungCheline.dto.Statistic;
import YoungCheline.YoungCheline.entity.Detail;
import YoungCheline.YoungCheline.entity.RestaurantEvaluate;
import YoungCheline.YoungCheline.repository.DetailRepository;
import YoungCheline.YoungCheline.repository.RestaurantEvaluateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DetailServiceImpl implements DetailService {

    private final DetailRepository detailRepository;
    private final RestaurantEvaluateRepository restaurantEvaluateRepository;
    public DetailDto getDetail(Integer menuId) {
        DetailDto detailDto = new DetailDto();
        Statistic[] statistic = new Statistic[2];
        ResultDto resultDto = new ResultDto();
        Detail detail = detailRepository.findByMenuId(menuId).get();
        RestaurantEvaluate restaurantEvaluate = restaurantEvaluateRepository.findByMenuId(menuId).get();
        detailDto.setMenuId(menuId);
        detailDto.setRestaurantId(restaurantEvaluate.getRestaurantId());

        if (restaurantEvaluate.getUrl() == null) {
            detailDto.setUrl(null);
        } else {
            detailDto.setUrl(restaurantEvaluate.getUrl());
        }

        resultDto.setFlavor(restaurantEvaluate.getFlavor());
        List<String> mood = checkMood(restaurantEvaluate.getCouple(), restaurantEvaluate.getFamily(), restaurantEvaluate.getSolo(), restaurantEvaluate.getFriend(), restaurantEvaluate.getDrink());
        resultDto.setMood(mood);
        resultDto.setPrice(restaurantEvaluate.getPrice());
        resultDto.setPlating(restaurantEvaluate.getPlating());
        resultDto.setService(restaurantEvaluate.getService());
        detailDto.setResultDto(resultDto);

        for (int i = 0; i < 2; i++) {
            statistic[i] = new Statistic();
        }
        statistic[0].setAvg(detail.getAvg1());
        statistic[0].setUrl(detail.getUrl1());
        statistic[1].setAvg(detail.getAvg2());
        statistic[1].setUrl(detail.getUrl2());
        detailDto.setStatistic(statistic);

        return detailDto;
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
