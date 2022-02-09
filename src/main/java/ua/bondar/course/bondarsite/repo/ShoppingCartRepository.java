package ua.bondar.course.bondarsite.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.bondar.course.bondarsite.model.ShoppingCart;

import java.util.List;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    ShoppingCart findByTokenSession(String sessionToken);
    List<ShoppingCart> findShoppingCartsByActive(Boolean active);
    List<ShoppingCart> findShoppingCartByBuyerNameAndCompliedOrder(String name, Boolean complied);
    ShoppingCart findShoppingCartById(Long id);
    void deleteById(Long id);
}
