package ua.bondar.course.bondarsite.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.bondar.course.bondarsite.model.CartItem;

@Repository
public interface CardItemRepository extends JpaRepository<CartItem, Long> {
}
