package YoungCheline.YoungCheline.repository;

import YoungCheline.YoungCheline.entity.Evaluate;
import YoungCheline.YoungCheline.entity.Key;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EvaluateRepository extends JpaRepository<Evaluate, Key> {
    Optional<Evaluate> findByKey_UserName(String userName);

    Optional<Evaluate> findFirstByKey_UserNameOrderByNumberDesc(String userName);
    Page<Evaluate> findByKey_UserNameAndNumberIsLessThanOrderByNumberDesc(String userName,Integer number, PageRequest pageRequest);
}
