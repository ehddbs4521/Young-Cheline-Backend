package YoungCheline.YoungCheline.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class User {

    @Id
    private String email;
    private String name;
    private String password;
    private boolean active;
    private String otp;
    private String token;
    private LocalDateTime OtpGeneratedTime;
}