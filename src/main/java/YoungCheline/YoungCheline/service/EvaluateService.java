package YoungCheline.YoungCheline.service;

import YoungCheline.YoungCheline.dto.KakaoMapDto;
import org.json.JSONException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface EvaluateService {
    KakaoMapDto saveRestaurant(KakaoMapDto kakaoMapDto);
    String getMenu(String restaurantId) throws JSONException;
    void evaluateMenu(String restaurantId, Integer menuId, String taste, String price, List<String> mood, String cleaning, String plating, String service, MultipartFile file, String userName, String time, String bucket) throws IOException;

}