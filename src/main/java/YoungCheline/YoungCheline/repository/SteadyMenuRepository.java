package YoungCheline.YoungCheline.repository;

import YoungCheline.YoungCheline.entity.SteadyMenu;
import YoungCheline.YoungCheline.entity.TimeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface SteadyMenuRepository extends JpaRepository<SteadyMenu, TimeKey> {
    Optional<SteadyMenu> findBySteadyMenuKey_TimeBetween(LocalDate startTime, LocalDate endTime);

}
