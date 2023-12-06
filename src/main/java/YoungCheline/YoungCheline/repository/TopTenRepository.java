package YoungCheline.YoungCheline.repository;

import YoungCheline.YoungCheline.entity.TopTen;
import YoungCheline.YoungCheline.entity.TopTenKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TopTenRepository extends JpaRepository<TopTen, TopTenKey> {
    List<TopTen> findByTopTenKey_UserName(String userName);
    Optional<TopTen> findByTopTenKey_UserNameAndTopTenKey_Ranking(String userName, String ranking);
    void deleteByTopTenKey_UserName(String userName);
    @Transactional
    void deleteByTopTenKey_UserNameAndMenuId(String userName,Integer menuId);
}
