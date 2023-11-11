package YoungCheline.YoungCheline.service;

import YoungCheline.YoungCheline.dto.LoginDto;
import YoungCheline.YoungCheline.repository.UserRepository;


public interface MyPageService {

    boolean changePw(String userName,String currentPw,String changePw);
}
