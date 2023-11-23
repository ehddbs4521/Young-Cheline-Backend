package YoungCheline.YoungCheline.repository;

import YoungCheline.YoungCheline.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByRestaurantId(String restaurantId);
    @Query("SELECT e FROM Menu e WHERE e.restaurantId = :restaurantId AND e.menuName = :menuName")
    Optional<Menu> findByRestaurantIdAndMenuName(@Param("restaurantId") String restaurantId, @Param("menuName") String menu);
    @Query("SELECT e FROM Menu e WHERE e.restaurantId = :restaurantId AND e.menuId = :menuId")
    Optional<Menu> findByRestaurantIdAndMenuId(@Param("restaurantId") String restaurantId, @Param("menuId") Integer menu);

}
