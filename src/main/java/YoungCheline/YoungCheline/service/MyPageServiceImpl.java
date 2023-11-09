package YoungCheline.YoungCheline.service;

import YoungCheline.YoungCheline.dto.LoginDto;
import YoungCheline.YoungCheline.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyPageServiceImpl implements MyPageService{

    @Autowired
    UserRepository userRepository;

    @Override
    public String changePw(LoginDto loginDto) {
        return null;
    }
    /*public String changePw(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(
                        () -> new RuntimeException("이메일을 찾을 수 없습니다: " + loginDto.getEmail())
                );
        if (user.getPassword().equals(loginDto.getPassword())) {
            return "기존 비밀번호와 동일합니다";
        } else {
            user.setPassword(loginDto.getPassword());
            userRepository.save(user);
        }
        return "비밀번호 변경 완료";
    }*/

}
