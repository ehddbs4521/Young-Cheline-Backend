package YoungCheline.YoungCheline.repository;

import YoungCheline.YoungCheline.entity.Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DetailRepository extends JpaRepository<Detail, Long> {

    Optional<Detail> findByMenuId(Integer menuId);
}
