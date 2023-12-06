package YoungCheline.YoungCheline.repository;

import YoungCheline.YoungCheline.entity.Evaluate;
import YoungCheline.YoungCheline.entity.Key;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EvaluateRepository extends JpaRepository<Evaluate, Key> {
    List<Evaluate> findAllByKey_UserName(String userName);

    Optional<Evaluate> findFirstByKey_UserNameOrderByIdDesc(String userName);
    Page<Evaluate> findAllByKey_UserNameAndIdIsLessThanOrderByIdDesc(String userName,Integer id, PageRequest pageRequest);
}
