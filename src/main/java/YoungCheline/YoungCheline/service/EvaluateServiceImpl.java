package YoungCheline.YoungCheline.service;

import YoungCheline.YoungCheline.dto.KakaoMapDto;
import YoungCheline.YoungCheline.entity.*;
import YoungCheline.YoungCheline.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    public String getMenu(String restaurantId) throws JSONException {

        List<String> menuName = menuRepository.findByRestaurantId(restaurantId).stream().map(Menu::getMenuName).collect(Collectors.toList());

        List<String> url = menuRepository.findByRestaurantId(restaurantId).stream().map(Menu::getUrl).collect(Collectors.toList());

        List<Integer> menuId = menuRepository.findByRestaurantId(restaurantId).stream().map(Menu::getMenuId).collect(Collectors.toList());
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < menuId.size(); i++) {
            JSONObject obj = new JSONObject();
            obj.put("menuId", menuId.get(i));
            obj.put("menuName", menuName.get(i));
            if (url.get(i) == null) {
                obj.put("url", "");
            } else {
                obj.put("url", url.get(i));
            }
            jsonArray.put(obj);
        }

        return jsonArray.toString();
    }

    public void evaluateMenu(String restaurantId, Integer menuId, String taste, String price, List<String> mood, String cleaning, String plating, String service, MultipartFile file, String userName, String time, String bucket) throws IOException {

        Evaluate evaluate = new Evaluate();
        List<Integer> num = evaluateRepository.findFirstByKey_UserNameOrderByIdDesc(userName).stream().map(Evaluate::getId).collect(Collectors.toList());
        int number = num.get(0);
        key.setMenuId(menuId);
        key.setTime(time);
        key.setUserName(userName);

        if (file!=null) {
            imageService.uploadMenuImage(file, bucket, menuId, time);
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

