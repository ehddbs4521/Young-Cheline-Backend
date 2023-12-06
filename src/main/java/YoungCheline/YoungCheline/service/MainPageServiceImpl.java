package YoungCheline.YoungCheline.service;

import YoungCheline.YoungCheline.dto.RestaurantEvaluateDto;
import YoungCheline.YoungCheline.dto.ResultDto;
import YoungCheline.YoungCheline.entity.RestaurantEvaluate;
import YoungCheline.YoungCheline.repository.RestaurantEvaluateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MainPageServiceImpl implements MainPageService {

    private static long count;
    private final RestaurantEvaluateRepository restaurantEvaluateRepository;

    public RestaurantEvaluateDto[] showEvaluateBox(Integer size, Integer number) {
        Integer maxId = restaurantEvaluateRepository.findMaxId();

        if (number == 0) {
            number = maxId + 1;
        }
        if (size >= number) {
            size = number - 1;
        }

        PageRequest pageRequest = PageRequest.of(0, size);
        Page<RestaurantEvaluate> page = restaurantEvaluateRepository.findByIdIsLessThanOrderByIdDesc(number, pageRequest);
        List<RestaurantEvaluate> content = page.getContent();
        RestaurantEvaluateDto[] restaurantEvaluateDto = new RestaurantEvaluateDto[size];

        for (int i = 0; i < size; i++) {
            restaurantEvaluateDto[i] = new RestaurantEvaluateDto();
            restaurantEvaluateDto[i].setId(number);
        }

        for (int i = 0; i < size; i++) {
            ResultDto resultDto = new ResultDto();
            String menuName = restaurantEvaluateRepository.findByRestaurantIdAndMenuId(content.get(i).getRestaurantId(), content.get(i).getMenuId()).get().getMenuName();

            List<String> mood = checkMood(content.get(i).getCouple(), content.get(i).getFamily(), content.get(i).getSolo(), content.get(i).getFriend(), content.get(i).getDrink());
            restaurantEvaluateDto[i].setRestaurantId(content.get(i).getRestaurantId());
            restaurantEvaluateDto[i].setMenuId(content.get(i).getMenuId());
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
            restaurantEvaluateDto[i].setId(content.get(i).getId());
            if (page.hasNext()) {
                restaurantEvaluateDto[i].setLast(false);
            } else {
                restaurantEvaluateDto[i].setLast(true);
            }


        }

        return restaurantEvaluateDto;

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

    public long totalDataCount(String menuName) {
        List<RestaurantEvaluate> content = restaurantEvaluateRepository.findByMenuNameContaining(menuName);
        this.count = content.stream().count();
        return count;
    }

    public RestaurantEvaluateDto[] showEvaluateBoxByKeyWord(String menuName, Integer size, Integer id, long total) {

        RestaurantEvaluate restaurantEvaluate = restaurantEvaluateRepository.findFirstByMenuNameContainingOrderByIdDesc(menuName).orElse(null);
        if (restaurantEvaluate == null) {
            return null;
        }
        Integer number = restaurantEvaluate.getId();
        if (size > total) {
            RestaurantEvaluateDto[] restaurantEvaluateDto = new RestaurantEvaluateDto[(int) total];
            PageRequest pageRequest = PageRequest.of(0, (int) total);
            Page<RestaurantEvaluate> page = restaurantEvaluateRepository.findByMenuNameContainingAndIdLessThanOrderByIdDesc(menuName, number+1, pageRequest);
            page.getContent().stream().forEach(System.out::println);
            List<RestaurantEvaluate> content = page.getContent();
            for (int i = 0; i < page.getNumberOfElements(); i++) {
                ResultDto resultDto = new ResultDto();

                restaurantEvaluateDto[i] = new RestaurantEvaluateDto();
                String menu = restaurantEvaluateRepository.findByRestaurantIdAndMenuId(content.get(i).getRestaurantId(), content.get(i).getMenuId()).get().getMenuName();
                List<String> mood = checkMood(content.get(i).getCouple(), content.get(i).getFamily(), content.get(i).getSolo(), content.get(i).getFriend(), content.get(i).getDrink());
                restaurantEvaluateDto[i].setRestaurantId(content.get(i).getRestaurantId());
                restaurantEvaluateDto[i].setMenuId(content.get(i).getMenuId());
                restaurantEvaluateDto[i].setMenuName(menu);

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
                restaurantEvaluateDto[i].setId(content.get(i).getId());
                if (page.hasNext()) {
                    restaurantEvaluateDto[i].setLast(false);
                } else {
                    restaurantEvaluateDto[i].setLast(true);
                }

            }

            return restaurantEvaluateDto;
        }

        if (id == 0) {
            id = number + 1;
        }

        PageRequest pageRequest = PageRequest.of(0, size);
        Page<RestaurantEvaluate> page = restaurantEvaluateRepository.findByMenuNameContainingAndIdLessThanOrderByIdDesc(menuName, id, pageRequest);
        page.getContent().stream().forEach(System.out::println);

        RestaurantEvaluateDto[] restaurantEvaluateDto = new RestaurantEvaluateDto[size];
        List<RestaurantEvaluate> content = page.getContent();
        for (int i = 0; i < page.getNumberOfElements(); i++) {
            ResultDto resultDto = new ResultDto();

            restaurantEvaluateDto[i] = new RestaurantEvaluateDto();
            String menu = restaurantEvaluateRepository.findByRestaurantIdAndMenuId(content.get(i).getRestaurantId(), content.get(i).getMenuId()).get().getMenuName();

            List<String> mood = checkMood(content.get(i).getCouple(), content.get(i).getFamily(), content.get(i).getSolo(), content.get(i).getFriend(), content.get(i).getDrink());
            restaurantEvaluateDto[i].setRestaurantId(content.get(i).getRestaurantId());
            restaurantEvaluateDto[i].setMenuId(content.get(i).getMenuId());
            restaurantEvaluateDto[i].setMenuName(menu);

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
            restaurantEvaluateDto[i].setId(content.get(i).getId());
            if (page.hasNext()) {
                restaurantEvaluateDto[i].setLast(false);
            } else {
                restaurantEvaluateDto[i].setLast(true);
            }
            log.info("menuName:{}",restaurantEvaluateDto[i].getMenuName());

        }
        return restaurantEvaluateDto;


    }

    public RestaurantEvaluateDto[] showEvaluateBoxByKeyWordFilter(Integer size, Integer id, List<RestaurantEvaluate> content) {
        boolean last;
        if (content.size() < size) {
            RestaurantEvaluateDto[] restaurantEvaluateDto = new RestaurantEvaluateDto[content.size()];

            for (int i = 0; i < content.size(); i++) {
                restaurantEvaluateDto[i] = new RestaurantEvaluateDto();
            }
            for (int i = 0; i < content.size(); i++) {
                ResultDto resultDto = new ResultDto();

                List<String> mood = checkMood(content.get(i).getCouple(), content.get(i).getFamily(), content.get(i).getSolo(), content.get(i).getFriend(), content.get(i).getDrink());
                restaurantEvaluateDto[i].setRestaurantId(content.get(i).getRestaurantId());
                restaurantEvaluateDto[i].setMenuId(content.get(i).getMenuId());
                restaurantEvaluateDto[i].setMenuName(content.get(i).getMenuName());

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
                restaurantEvaluateDto[i].setId(content.get(i).getId());
                restaurantEvaluateDto[i].setLast(true);
            }
            return restaurantEvaluateDto;
        }
        int number = id;

        if (number == 0) {
            Integer num = content.stream().sorted(Comparator.comparing(RestaurantEvaluate::getId).reversed()).collect(Collectors.toList()).get(0).getId();
            number = num + 1;
        }

        int finalNumber = number;
        long total = content.stream()
                .filter(item -> (item.getId() < finalNumber)).count();
        List<RestaurantEvaluate> filteredContent = content.stream()
                .filter(item -> (item.getId() < finalNumber))
                .sorted(Comparator.comparing(RestaurantEvaluate::getId).reversed())
                .limit(size)
                .collect(Collectors.toList());

        if (total - filteredContent.size() <= 0) {
            last = true;
        } else {
            last = false;
        }
        RestaurantEvaluateDto[] restaurantEvaluateDto = new RestaurantEvaluateDto[size];
        for (int i = 0; i < size; i++) {
            restaurantEvaluateDto[i] = new RestaurantEvaluateDto();
        }
        for (int i = 0; i < size; i++) {
            ResultDto resultDto = new ResultDto();

            List<String> mood = checkMood(filteredContent.get(i).getCouple(), filteredContent.get(i).getFamily(), filteredContent.get(i).getSolo(), filteredContent.get(i).getFriend(), filteredContent.get(i).getDrink());
            restaurantEvaluateDto[i].setRestaurantId(filteredContent.get(i).getRestaurantId());
            restaurantEvaluateDto[i].setMenuId(filteredContent.get(i).getMenuId());
            restaurantEvaluateDto[i].setMenuName(filteredContent.get(i).getMenuName());

            resultDto.setFlavor(filteredContent.get(i).getFlavor());
            resultDto.setPrice(filteredContent.get(i).getPrice());
            resultDto.setCleanliness(filteredContent.get(i).getCleaning());
            resultDto.setPlating(filteredContent.get(i).getPlating());
            resultDto.setService(filteredContent.get(i).getService());
            resultDto.setMood(mood);

            restaurantEvaluateDto[i].setEvaluate(resultDto);
            if (filteredContent.get(i).getUrl() == null) {
                restaurantEvaluateDto[i].setUrl(null);
            } else {
                restaurantEvaluateDto[i].setUrl(filteredContent.get(i).getUrl());
            }
            restaurantEvaluateDto[i].setId(filteredContent.get(i).getId());
            restaurantEvaluateDto[i].setLast(last);
        }
        return restaurantEvaluateDto;


    }

    public List<RestaurantEvaluate> init(String menuName, List<String> flavor, List<String> mood, List<String> plating, List<String> cleaning, List<String> service) {
        List<String> couple = new ArrayList<>();
        List<String> drink = new ArrayList<>();
        List<String> family = new ArrayList<>();
        List<String> friend = new ArrayList<>();
        List<String> solo = new ArrayList<>();

        for (String numb : mood) {
            if (numb.equals("0")) {
                couple.add("0");
            }
            if (numb.equals("3")) {
                drink.add("3");
            }
            if (numb.equals("1")) {
                family.add("1");
            }
            if (numb.equals("4")) {
                friend.add("4");
            }
            if (numb.equals("2")) {
                solo.add("2");
            }
        }

        Specification<RestaurantEvaluate> restaurantEvaluateSpecification = restaurantEvaluateRepository.buildSpecification(menuName, flavor, plating, cleaning, service, couple, family, solo, drink, friend);
        List<RestaurantEvaluate> content = restaurantEvaluateRepository.findAll(restaurantEvaluateSpecification);

        return content;
    }
}
