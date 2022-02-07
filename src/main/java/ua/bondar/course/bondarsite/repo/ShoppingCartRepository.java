package ua.bondar.course.bondarsite.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.bondar.course.bondarsite.model.ShoppingCart;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    ShoppingCart findByTokenSession(String sessionToken);
}
