package YoungCheline.YoungCheline.repository;

import YoungCheline.YoungCheline.entity.RestaurantEvaluate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantEvaluateRepository extends JpaRepository<RestaurantEvaluate, Long> {

    Optional<RestaurantEvaluate> findByMenuId(Integer menuId);
}
