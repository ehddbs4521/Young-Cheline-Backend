package YoungCheline.YoungCheline.service;

import YoungCheline.YoungCheline.dto.DetailDto;

import java.util.List;

public interface DetailService {

    DetailDto getDetail(Integer menuId);
    List<String> checkMood(String couple, String family, String solo, String friend, String drink);
}
