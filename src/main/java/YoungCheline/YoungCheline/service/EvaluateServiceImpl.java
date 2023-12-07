package YoungCheline.YoungCheline.service;

import YoungCheline.YoungCheline.dto.GetMenuDto;
import YoungCheline.YoungCheline.dto.KakaoMapDto;
import YoungCheline.YoungCheline.entity.Evaluate;
import YoungCheline.YoungCheline.entity.KakaoMap;
import YoungCheline.YoungCheline.entity.Key;
import YoungCheline.YoungCheline.entity.Menu;
import YoungCheline.YoungCheline.repository.EvaluateRepository;
import YoungCheline.YoungCheline.repository.KakoMapRepository;
import YoungCheline.YoungCheline.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class EvaluateServiceImpl implements EvaluateService {

    private final ImageServiceImpl imageService;
    private final MenuRepository menuRepository;
    private final KakoMapRepository kakoMapRepository;
    private final EvaluateRepository evaluateRepository;
    private Key key = new Key();


    public KakaoMapDto saveRestaurant(KakaoMapDto kakaoMapDto) {
        Optional<KakaoMap> map = kakoMapRepository.findByRestaurantId(kakaoMapDto.getRestaurantId());
        if (map.isEmpty()) {
            KakaoMap kakaoMap = new KakaoMap();
            System.out.println(111111);
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

    public GetMenuDto[] getMenu(String restaurantId) {
        List<String> menuName = menuRepository.findByRestaurantId(restaurantId).stream().map(Menu::getMenuName).collect(Collectors.toList());

        List<String> url = menuRepository.findByRestaurantId(restaurantId).stream().map(Menu::getUrl).collect(Collectors.toList());

        List<Integer> menuId = menuRepository.findByRestaurantId(restaurantId).stream().map(Menu::getMenuId).collect(Collectors.toList());
        int cnt = menuId.size();
        GetMenuDto[] getMenuDtos = new GetMenuDto[cnt];

        for (int i = 0; i < cnt; i++) {
            getMenuDtos[i] = new GetMenuDto();
            getMenuDtos[i].setMenuId(menuId.get(i));
            getMenuDtos[i].setMenuName(menuName.get(i));
            getMenuDtos[i].setUrl(url.get(i));
        }


        return getMenuDtos;
    }

    public void evaluateMenu(String restaurantId, Integer menuId, String taste, String price, List<String> mood, String cleaning, String plating, String service, MultipartFile file, String userName, LocalDateTime time, String bucket) throws IOException {
        Evaluate evaluate = new Evaluate();
        String url;
        int number;
        Optional<Evaluate> firstByKeyUserNameOrderByIdDesc = evaluateRepository.findFirstByKey_UserNameOrderByIdDesc(userName);
        if (!firstByKeyUserNameOrderByIdDesc.isEmpty()) {
            number = firstByKeyUserNameOrderByIdDesc.get().getId();
        } else {
            number=0;
        }

        log.info("number:{}", number);
        key.setMenuId(menuId);
        key.setTime(time);
        key.setUserName(userName);

        if (file!=null) {
            url = imageService.uploadMenuImage(file, bucket, menuId, time);
            evaluate.setUrl(url);
        }

        evaluate.setKey(key);
        evaluate.setRestaurantId(restaurantId);
        evaluate.setFlavor(taste);
        evaluate.setPrice(price);
        evaluate.setCleaning(cleaning);
        evaluate.setPlating(plating);
        evaluate.setService(service);

        for (String feel:mood) {
            if (feel.equals("0")) {
                evaluate.setCouple("0");
            }
            if (feel.equals("1")) {
                evaluate.setFamily("1");
            }
            if (feel.equals("2")) {
                evaluate.setSolo("2");
            }
            if (feel.equals("3")) {
                evaluate.setDrink("3");
            }
            if (feel.equals("4")) {
                evaluate.setFriend("4");
            }
        }
        evaluate.setId(number + 1);

        evaluateRepository.save(evaluate);


    }


}

