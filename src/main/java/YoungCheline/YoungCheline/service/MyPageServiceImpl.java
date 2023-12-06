package YoungCheline.YoungCheline.service;

import YoungCheline.YoungCheline.dto.RestaurantEvaluateDto;
import YoungCheline.YoungCheline.dto.ResultDto;
import YoungCheline.YoungCheline.dto.TopTenDto;
import YoungCheline.YoungCheline.entity.Evaluate;
import YoungCheline.YoungCheline.entity.TopTen;
import YoungCheline.YoungCheline.entity.TopTenKey;
import YoungCheline.YoungCheline.entity.User;
import YoungCheline.YoungCheline.exception.AppException;
import YoungCheline.YoungCheline.exception.ErrorCode;
import YoungCheline.YoungCheline.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final EvaluateRepository evaluateRepository;
    private final MenuRepository menuRepository;
    private final TopTenRepository topTenRepository;
    private final ImageRepository imageRepository;


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
        Integer number = evaluateRepository.findFirstByKey_UserNameOrderByIdDesc(userName).get().getId();
        List<Evaluate> evaluates = evaluateRepository.findAllByKey_UserName(userName);
        int count= (int) evaluates.stream().count();
        int total=size;
        if (lastNumber == 0) {
            lastNumber = number + 1;
        }
        if (size >count) {
            size=count;
        }

        PageRequest pageRequest = PageRequest.of(0, size);
        Page<Evaluate> page = evaluateRepository.findAllByKey_UserNameAndIdIsLessThanOrderByIdDesc(userName, lastNumber, pageRequest);
        List<Evaluate> content = page.getContent();
        RestaurantEvaluateDto[] restaurantEvaluateDto = new RestaurantEvaluateDto[size];

        for (int i = 0; i < size; i++) {
            restaurantEvaluateDto[i] = new RestaurantEvaluateDto();
            restaurantEvaluateDto[i].setId(content.get(i).getId());
            if (total > count) {
                restaurantEvaluateDto[i].setLast(true);
            } else {
                restaurantEvaluateDto[i].setLast(false);
            }
        }

        for (int i = 0; i < size; i++) {

            String menuName = menuRepository.findByRestaurantIdAndMenuId(content.get(i).getRestaurantId(), content.get(i).getKey().getMenuId()).get().getMenuName();
            ResultDto resultDto = new ResultDto();

            List<String> mood = checkMood(content.get(i).getCouple(), content.get(i).getFamily(), content.get(i).getSolo(), content.get(i).getFriend(), content.get(i).getDrink());
            restaurantEvaluateDto[i].setRestaurantId(content.get(i).getRestaurantId());
            restaurantEvaluateDto[i].setMenuId(content.get(i).getKey().getMenuId());
            restaurantEvaluateDto[i].setMenuName(menuName);

            resultDto.setFlavor(content.get(i).getFlavor());
            resultDto.setPrice(content.get(i).getPrice());
            resultDto.setCleanliness(content.get(i).getCleaning());
            resultDto.setPlating(content.get(i).getPlating());
            resultDto.setService(content.get(i).getService());
            resultDto.setMood(mood);

            restaurantEvaluateDto[i].setEvaluate(resultDto);
            if (content.get(i).getUrl() == null) {
                restaurantEvaluateDto[i].setUrl(null);
            } else {
                restaurantEvaluateDto[i].setUrl(content.get(i).getUrl());
            }
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

    public TopTenDto[] showTop10List(String userName) {
        List<TopTen> menu = topTenRepository.findByTopTenKey_UserName(userName);
        TopTenDto[] topTen = new TopTenDto[menu.size()];

        for (int i = 0; i < menu.size(); i++) {
            topTen[i] = new TopTenDto();
            ResultDto resultDto = new ResultDto();
            topTen[i].setRestaurantId(menu.get(i).getRestaurantId());
            topTen[i].setMenuId(menu.get(i).getMenuId());
            topTen[i].setMenuName(menu.get(i).getMenuName());
            List<String> mood = checkMood(menu.get(i).getCouple(), menu.get(i).getFamily(), menu.get(i).getSolo(), menu.get(i).getFriend(), menu.get(i).getDrink());

            resultDto.setFlavor(menu.get(i).getFlavor());
            resultDto.setPrice(menu.get(i).getPrice());
            resultDto.setCleanliness(menu.get(i).getCleaning());
            resultDto.setPlating(menu.get(i).getPlating());
            resultDto.setService(menu.get(i).getService());
            resultDto.setMood(mood);

            topTen[i].setEvaluate(resultDto);
            if (menu.get(i).getUrl() == null) {
                topTen[i].setUrl(null);
            } else {
                topTen[i].setUrl(menu.get(i).getUrl());
            }
            topTen[i].setRank(menu.get(i).getTopTenKey().getRanking());
        }

        return topTen;
    }

    public boolean sendTop10List(TopTenDto[] topTenDto, String userName) {
        long count = Arrays.stream(topTenDto).count();
        long distinctCount = Arrays.stream(topTenDto).toList().stream().map(TopTenDto::getMenuId).distinct().count();

        System.out.println(count);
        System.out.println(distinctCount);
        if (count != distinctCount) {
            return false;
        }
        for (int i = 0; i < count; i++) {
            Optional<TopTen> ten = topTenRepository.findByTopTenKey_UserNameAndTopTenKey_Ranking(userName, topTenDto[i].getRank());
            TopTenKey topTenKey = new TopTenKey();
            List<String> mood = topTenDto[i].getEvaluate().getMood();
            if (ten.isEmpty()) {
                TopTen topTen = new TopTen();
                updateTop10(userName, topTenKey, mood, topTen, topTenDto[i]);
                topTenRepository.save(topTen);
            } else {
                TopTen topTen = ten.get();
                updateTop10(userName, topTenKey, mood, topTen, topTenDto[i]);
                topTenRepository.save(topTen);
            }

        }
        return true;
    }

    private void updateTop10(String userName, TopTenKey topTenKey, List<String> mood, TopTen topTen, TopTenDto topTenDto) {
        topTen.setMenuId(topTenDto.getMenuId());
        topTen.setRestaurantId(topTenDto.getRestaurantId());
        topTen.setMenuName(topTenDto.getMenuName());
        topTen.setFlavor(topTenDto.getEvaluate().getFlavor());
        topTen.setCleaning(topTenDto.getEvaluate().getCleanliness());
        topTen.setService(topTenDto.getEvaluate().getService());
        topTen.setPrice(topTenDto.getEvaluate().getPrice());
        topTen.setPlating(topTenDto.getEvaluate().getPlating());
        setMood(topTen, mood);
        topTen.setUrl(topTenDto.getUrl());
        topTenKey.setUserName(userName);
        topTenKey.setRanking(topTenDto.getRank());
        topTen.setTopTenKey(topTenKey);
    }

    private void setMood(TopTen topTen, List<String> mood) {
        for (String feel : mood) {
            if (feel.equals("0")) {
                topTen.setCouple("0");
            }
            if (feel.equals("1")) {
                topTen.setFamily("1");
            }
            if (feel.equals("2")) {
                topTen.setSolo("2");
            }
            if (feel.equals("3")) {
                topTen.setDrink("3");
            }
            if (feel.equals("4")) {
                topTen.setFriend("4");
            }
        }
    }

    public void withdraw(String userName) {
        userRepository.deleteByUserName(userName);
        imageRepository.deleteByUserName(userName);
        topTenRepository.deleteByTopTenKey_UserName(userName);
    }

    public void deleteTop10List(TopTenDto topTenDto, String userName) {
        topTenRepository.deleteByTopTenKey_UserName(userName);
    }
}
