package ua.bondar.course.bondarsite.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.bondar.course.bondarsite.model.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findProductById(Long id);
    List<Product> findProductByActive(Boolean active);
}
