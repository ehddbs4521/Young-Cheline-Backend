package YoungCheline.YoungCheline.service;

import YoungCheline.YoungCheline.dto.LoginDto;
import YoungCheline.YoungCheline.dto.RestaurantEvaluateDto;
import YoungCheline.YoungCheline.dto.TopTenDto;
import YoungCheline.YoungCheline.repository.UserRepository;

import java.util.Map;


public interface MyPageService {

    Map<String, String> changePw(String userName, String changePw);
    boolean isSamePwEach(String userName, String changePw,String currentPw);
    RestaurantEvaluateDto[] showEvaluateList(int size, int lastNumber, String userName);
    TopTenDto[] showTop10List(String userName);
    boolean sendTop10List(TopTenDto[] topTenDto, String userName);
    void withdraw(String userName);
}
