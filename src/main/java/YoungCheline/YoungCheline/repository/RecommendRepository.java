package YoungCheline.YoungCheline.repository;

import YoungCheline.YoungCheline.entity.Recommend;
import YoungCheline.YoungCheline.entity.RecommendKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RecommendRepository extends JpaRepository<Recommend, RecommendKey> {
    List<Recommend> findAllByRecommendKey_UserNameAndRecommendKey_TimeBetween(String userName, LocalDate startTime, LocalDate endTime);
}
