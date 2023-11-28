package YoungCheline.YoungCheline.repository;

import YoungCheline.YoungCheline.entity.HotMenu;
import YoungCheline.YoungCheline.entity.Recommend;
import YoungCheline.YoungCheline.entity.TimeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface HotMenuRepository extends JpaRepository<HotMenu, TimeKey> {
    Optional<HotMenu> findByHotMenuKey_TimeBetween(LocalDate startTime, LocalDate endTime);

}
