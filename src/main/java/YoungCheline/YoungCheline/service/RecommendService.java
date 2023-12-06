package YoungCheline.YoungCheline.service;

import YoungCheline.YoungCheline.dto.RecommendDto;

import java.util.List;

public interface RecommendService {
    boolean checkRecommend(String userName);
    RecommendDto[] getRecommend(String userName);
    List<String> checkMood(String couple, String family, String solo, String friend, String drink);
}
