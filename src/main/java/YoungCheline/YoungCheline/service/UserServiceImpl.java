package YoungCheline.YoungCheline.service;

import YoungCheline.YoungCheline.dto.JwtDto;
import YoungCheline.YoungCheline.dto.ProfileDto;
import YoungCheline.YoungCheline.dto.RegisterDto;
import YoungCheline.YoungCheline.entity.Profile;
import YoungCheline.YoungCheline.entity.User;
import YoungCheline.YoungCheline.repository.ImageRepository;
import YoungCheline.YoungCheline.repository.UserRepository;
import YoungCheline.YoungCheline.util.EmailUtil;
import YoungCheline.YoungCheline.util.JwtTokenUtil;
import YoungCheline.YoungCheline.util.TemporaryPwUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final EmailUtil emailUtil;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final TemporaryPwUtil temporaryPwUtil;
    private final JwtDto jwtDto;
    private final RegisterDto registerDto;
    @Value("${secretkey}")
    private String secretKey;
    private Long expiredMs = 1000 * 60 * 60 * 24 * 50L;
    @Value("default.profile")
    private String defaultProfile;

    public boolean register(String userName, String password, String email) {
        if (checkUserByEmail(email)) {
            Optional<User> userOpt = userRepository.findByEmail(email);
            User user = userOpt.get();
            if (user.getUserName() == null || user.getUserName() != userName) {
                Profile profile = new Profile();
                profile.setUserName(userName);
                profile.setUrl(defaultProfile);
                user.setUserName(userName);
                user.setPassword(encoder.encode(password));
                user.setEmail(email);
                profile.setUserName(userName);
                registerDto.setUserName(userName);
                registerDto.setPassword(password);
                registerDto.setEmail(email);
                imageRepository.save(profile);
                userRepository.save(user);
                return true;
            }
            return false;
        }

        return false;
    }

    public boolean sendEmail(String email) throws MessagingException {
        if (!userRepository.existsByEmail(email)) {
            emailUtil.sendEmail(email);
            User user = new User();
            user.setEmail(email);
            user.setActive(false);
            user.setTime(LocalDateTime.now());
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean validateEmail(String email) {
        if (checkUserByEmail(email)) {
            Optional<User> userOpt = userRepository.findByEmail(email);
            User user = userOpt.get();
            if (Duration.between(user.getTime(), LocalDateTime.now()).getSeconds() < (1 * 60 * 60 * 10)) {
                user.setActive(true);
                userRepository.save(user);
                return true;
            }
            return false;
        }

        return false;
    }

    public boolean login(String userName, String password) {
        if (getUserByUserName(userName)) {
            Optional<User> userOpt = userRepository.findByUserName(userName);
            User user = userOpt.get();
            if (encoder.matches(password, user.getPassword()) || encoder.matches(password, user.getTempPw())) {
                System.out.println(encoder.matches(password, user.getPassword()));
                System.out.println(encoder.matches(password, user.getTempPw()));
                jwtDto.setToken(JwtTokenUtil.createJwt(user.getUserName(), secretKey, expiredMs));
                log.info("jwt token:{}", jwtDto.getToken());
                return true;
            }
            return false;
        }
        return false;

    }

    private boolean getUserByUserName(String userName) {

        Optional<User> user = userRepository.findByUserName(userName);
        if (user.isPresent()) {
            return true;
        }
        return false;
    }

    public boolean findPwByEmail(String email) throws MessagingException {
        if (checkUserByEmail(email)) {
            Optional<User> userOpt = userRepository.findByEmail(email);
            User user = userOpt.get();
            String tempPw = temporaryPwUtil.createTempPw();
            user.setTempPw(encoder.encode(tempPw));
            userRepository.save(user);
            emailUtil.sendTempPwEmail(email, tempPw);
            return true;
        }
        return false;
    }

    private boolean checkUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return true;
        }

        return false;
    }

    public boolean findID(String email) {
        if (checkUserByEmail(email)) {
            Optional<User> user = userRepository.findByEmail(email);
            return true;
        }
        return false;
    }

    public boolean validateDuplicateId(String id) {
        Optional<User> user = userRepository.findByUserName(id);

        return user.isEmpty();

    }

    public User getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.get();
    }

    public ProfileDto getProfile(String userName) {
        ProfileDto profileDto = new ProfileDto();

        String url = imageRepository.findByUserName(userName).get().getUrl();
        profileDto.setUrl(url);

        if (userName == null) {
            profileDto.setUserName(null);
            return profileDto;
        }
        profileDto.setUserName(userName);

        return profileDto;
    }
}
