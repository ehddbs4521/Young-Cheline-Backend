package YoungCheline.YoungCheline.service;

import YoungCheline.YoungCheline.dto.LoginDto;
import YoungCheline.YoungCheline.repository.UserRepository;

import java.util.Map;


public interface MyPageService {

    Map<String,String> changePw(String userName,String changePw);
}
