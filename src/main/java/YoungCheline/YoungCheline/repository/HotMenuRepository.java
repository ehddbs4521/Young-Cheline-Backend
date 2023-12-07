package YoungCheline.YoungCheline.repository;

import YoungCheline.YoungCheline.entity.HotMenu;
import YoungCheline.YoungCheline.entity.Recommend;
import YoungCheline.YoungCheline.entity.TimeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface HotMenuRepository extends JpaRepository<HotMenu, TimeKey> {
    List<HotMenu> findAllByHotMenuKey_TimeBetween(LocalDate startTime, LocalDate endTime);

}
