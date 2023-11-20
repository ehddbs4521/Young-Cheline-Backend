package YoungCheline.YoungCheline.repository;

import YoungCheline.YoungCheline.entity.KakaoMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KakoMapRepository extends JpaRepository<KakaoMap, Long> {
    Optional<KakaoMap> findByRestaurantId(String restaurantId);
}
