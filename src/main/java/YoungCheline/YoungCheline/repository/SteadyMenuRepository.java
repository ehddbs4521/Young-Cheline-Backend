package YoungCheline.YoungCheline.repository;

import YoungCheline.YoungCheline.entity.SteadyMenu;
import YoungCheline.YoungCheline.entity.TimeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SteadyMenuRepository extends JpaRepository<SteadyMenu, TimeKey> {
    List<SteadyMenu> findAllBySteadyMenuKey_TimeBetween(LocalDate startTime, LocalDate endTime);

}
