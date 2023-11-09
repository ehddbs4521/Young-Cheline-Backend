package YoungCheline.YoungCheline.service;

import jakarta.mail.MessagingException;

public interface UserService {

    String register(String userName,String password,String email);

    String sendEmail(String email) throws MessagingException;
    String validateEmail(String email);
    String login(String userName, String password);
    String findPW(String email) throws MessagingException;
}
