package YoungCheline.YoungCheline.service;

import YoungCheline.YoungCheline.dto.LoginDto;
import YoungCheline.YoungCheline.repository.UserRepository;


public interface MyPageService {

    public String changePw(LoginDto loginDto);
}
