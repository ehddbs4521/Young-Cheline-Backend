package YoungCheline.YoungCheline.service;

import jakarta.mail.MessagingException;

public interface UserService {

    boolean register(String userName, String password, String email);

    boolean findPwByEmail(String email) throws MessagingException;
    boolean validateEmail(String email);
    boolean login(String userName, String password);
    boolean findID(String email);

    boolean validateDuplicateId(String id);
}
