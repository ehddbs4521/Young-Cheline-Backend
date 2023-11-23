package YoungCheline.YoungCheline.service;

import YoungCheline.YoungCheline.dto.RestaurantEvaluateDto;
import YoungCheline.YoungCheline.entity.*;
import YoungCheline.YoungCheline.exception.AppException;
import YoungCheline.YoungCheline.exception.ErrorCode;
import YoungCheline.YoungCheline.repository.EvaluateRepository;
import YoungCheline.YoungCheline.repository.MenuRepository;
import YoungCheline.YoungCheline.repository.RestaurantEvaluateRepository;
import YoungCheline.YoungCheline.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final RestaurantEvaluateRepository restaurantEvaluateRepository;
    private final EvaluateRepository evaluateRepository;
    private final MenuRepository menuRepository;


    public Map<String, String> changePw(String userName, String changePw) {
        Map<String, String> pwMap = new HashMap<>();
        User user = userRepository.findByUserName(userName)
                .orElseThrow(
                        () -> new AppException(ErrorCode.BAD_REQUEST, "사용자를 찾을 수 없습니다.")
                );

        user.setPassword(encoder.encode(changePw));
        userRepository.save(user);
        pwMap.put("password", changePw);
        return pwMap;

    }

    public boolean isSamePwEach(String userName, String changePw) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(
                        () -> new AppException(ErrorCode.BAD_REQUEST, "사용자를 찾을 수 없습니다.")
                );
        if (encoder.matches(changePw, user.getPassword())) {
            return true;
        }
        return false;
    }

    public RestaurantEvaluateDto[] showEvaluateList(int size, int lastNumber, String userName) {
        Integer number = evaluateRepository.findFirstByKey_UserNameOrderByNumberDesc(userName).get().getNumber();
        if (lastNumber == 0) {
            lastNumber=number+1;
        }
        PageRequest pageRequest = PageRequest.of(0, size);
        Page<Evaluate> page = evaluateRepository.findByKey_UserNameAndNumberIsLessThanOrderByNumberDesc(userName, lastNumber, pageRequest);
        List<Evaluate> content = page.getContent();
        RestaurantEvaluateDto[] restaurantEvaluateDto = new RestaurantEvaluateDto[size];

        for (int i = 0; i < size; i++) {
            restaurantEvaluateDto[i] = new RestaurantEvaluateDto();
            if (lastNumber == 0) {
                restaurantEvaluateDto[i].setNumber(number);
            }
        }

        for (int i = 0; i < size; i++) {

            String menuName = menuRepository.findByRestaurantIdAndMenuId(content.get(i).getRestaurantId(), content.get(i).getKey().getMenuId()).get().getMenuName();

            List<String> mood = checkMood(content.get(i).getCouple(), content.get(i).getFamily(), content.get(i).getSolo(), content.get(i).getFriend(), content.get(i).getDrink());
            restaurantEvaluateDto[i].setRestaurantId(content.get(i).getRestaurantId());
            restaurantEvaluateDto[i].setMenuId(content.get(i).getKey().getMenuId());
            restaurantEvaluateDto[i].setRestaurantId(content.get(i).getRestaurantId());
            restaurantEvaluateDto[i].setMenuName(menuName);
            restaurantEvaluateDto[i].setTaste(content.get(i).getTaste());
            restaurantEvaluateDto[i].setPrice(content.get(i).getPrice());
            restaurantEvaluateDto[i].setCleaning(content.get(i).getCleaning());
            restaurantEvaluateDto[i].setPlating(content.get(i).getPlating());
            restaurantEvaluateDto[i].setService(content.get(i).getService());
            restaurantEvaluateDto[i].setMood(mood);
            restaurantEvaluateDto[i].setUrl(content.get(i).getUrl());
            restaurantEvaluateDto[i].setNumber(content.get(i).getNumber());
        }

        return restaurantEvaluateDto;

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
