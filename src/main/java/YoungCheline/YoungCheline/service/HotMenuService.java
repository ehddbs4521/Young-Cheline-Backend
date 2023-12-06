package YoungCheline.YoungCheline.service;

import YoungCheline.YoungCheline.dto.RecommendDto;

import java.util.List;

public interface HotMenuService {
    RecommendDto[] getHotMenu();
    List<String> checkMood(String couple, String family, String solo, String friend, String drink);
}
