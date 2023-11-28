package YoungCheline.YoungCheline.repository;

import YoungCheline.YoungCheline.entity.Recommend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface RecommendRepository extends JpaRepository<Recommend, Long> {
    Optional<Recommend> findByRecommendKey_UserNameAndRecommendKey_TimeBetween(String userName, LocalDate startTime, LocalDate endTime);
}
