package YoungCheline.YoungCheline.repository;

import YoungCheline.YoungCheline.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUserName(String user);
    @Transactional
    void deleteByUserName(String userName);
}
