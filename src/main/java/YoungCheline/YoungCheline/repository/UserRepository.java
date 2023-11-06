package YoungCheline.YoungCheline.repository;

import YoungCheline.YoungCheline.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUserName(String userName);
    Boolean existsByEmail(String email);

}