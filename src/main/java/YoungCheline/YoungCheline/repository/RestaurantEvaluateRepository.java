package YoungCheline.YoungCheline.repository;

import YoungCheline.YoungCheline.entity.RestaurantEvaluate;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantEvaluateRepository extends JpaRepository<RestaurantEvaluate, Long> {

    @Query("SELECT MAX(r.id) FROM RestaurantEvaluate r")
    Integer findMaxId();

    Page<RestaurantEvaluate> findByIdIsLessThanOrderByIdDesc(Integer id, PageRequest pageRequest);

    Optional<RestaurantEvaluate> findByRestaurantIdAndMenuId(String restaurantId, Integer menuId);

    Page<RestaurantEvaluate> findByMenuNameContainingAndIdLessThanOrderByIdDesc(String menuName, int id, PageRequest pageRequest);

    Optional<RestaurantEvaluate> findFirstByMenuNameContainingOrderByIdDesc(String menuName);

    List<RestaurantEvaluate> findByMenuNameContaining(String menuName);

    List<RestaurantEvaluate> findAll(Specification<RestaurantEvaluate> specification);

    default Specification<RestaurantEvaluate> buildSpecification(String menuName,
                                                                 List<String> flavor,
                                                                 List<String> plating, List<String> cleaning,
                                                                 List<String> service, List<String> couple,
                                                                 List<String> family, List<String> solo,
                                                                 List<String> drink, List<String> friend) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Add conditions based on provided parameters
            if (menuName != null && !menuName.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("menuName"), "%" + menuName + "%"));
            }
            if (flavor != null && !flavor.isEmpty()) {
                predicates.add(root.get("flavor").in(flavor));
            }
            if (plating != null && !plating.isEmpty()) {
                predicates.add(root.get("plating").in(plating));
            }
            if (cleaning != null && !cleaning.isEmpty()) {
                predicates.add(root.get("cleaning").in(cleaning));
            }
            if (service != null && !service.isEmpty()) {
                predicates.add(root.get("service").in(service));
            }
            if (couple != null && !couple.isEmpty()) {
                predicates.add(root.get("couple").in(couple));
            }
            if (family != null && !family.isEmpty()) {
                predicates.add(root.get("family").in(family));
            }
            if (solo != null && !solo.isEmpty()) {
                predicates.add(root.get("solo").in(solo));
            }
            if (drink != null && !drink.isEmpty()) {
                predicates.add(root.get("drink").in(drink));
            }
            if (friend != null && !friend.isEmpty()) {
                predicates.add(root.get("friend").in(friend));
            }

            // Combine all predicates using AND operation
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
