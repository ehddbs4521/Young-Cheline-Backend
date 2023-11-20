package YoungCheline.YoungCheline.service;

import YoungCheline.YoungCheline.dto.KakaoMapDto;
import YoungCheline.YoungCheline.dto.MenuDto;
import YoungCheline.YoungCheline.entity.KakaoMap;
import YoungCheline.YoungCheline.entity.Menu;
import YoungCheline.YoungCheline.repository.KakoMapRepository;
import YoungCheline.YoungCheline.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class EvaluateServiceImpl {

    private final MenuRepository menuRepository;
    private final KakoMapRepository kakoMapRepository;

    public KakaoMapDto saveRestaurant(KakaoMapDto kakaoMapDto) {
        Optional<KakaoMap> map = kakoMapRepository.findByRestaurantId(kakaoMapDto.getRestaurantId());
        if (map.isEmpty()) {
            KakaoMap kakaoMap = new KakaoMap();

            kakaoMap.setRestaurantId(kakaoMapDto.getRestaurantId());
            kakaoMap.setPlace_name(kakaoMapDto.getPlace_name());
            kakaoMap.setRoad_address_name(kakaoMapDto.getRoad_address_name());
            kakaoMap.setX(kakaoMapDto.getX());
            kakaoMap.setY(kakaoMapDto.getY());
            kakoMapRepository.save(kakaoMap);
            return kakaoMapDto;
        }
        return kakaoMapDto;
    }

    public MenuDto getMenu(String restaurantId) {

        List<String> menu = menuRepository.findByRestaurantId(restaurantId).stream()
                .map(Menu::getMenu)
                .collect(Collectors.toList());

        List<String> url = menuRepository.findByRestaurantId(restaurantId).stream()
                .map(Menu::getUrl)
                .collect(Collectors.toList());

        List<String> menuId = menuRepository.findByRestaurantId(restaurantId).stream()
                .map(Menu::getMenuId)
                .collect(Collectors.toList());

        MenuDto menuDto = new MenuDto();
        menuDto.setRestaurantId(restaurantId);
        menuDto.setMenu(menu);
        menuDto.setUrl(url);
        menuDto.setMenuId(menuId);
        return menuDto;
    }

    public MenuDto evaluateTaste(String userName,String restaurantId, List<String> menuId) {

    }
}
