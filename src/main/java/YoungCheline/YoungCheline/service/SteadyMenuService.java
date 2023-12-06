package YoungCheline.YoungCheline.service;

import YoungCheline.YoungCheline.dto.RecommendDto;

import java.util.List;

public interface SteadyMenuService {
    RecommendDto[] getSteadyMenu();
    List<String> checkMood(String couple, String family, String solo, String friend, String drink);
}
