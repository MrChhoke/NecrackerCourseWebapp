package ua.bondar.course.bondarsite.service;

import ua.bondar.course.bondarsite.model.item.Product;

import java.util.List;

public interface ProductService {
    Product getProductById(Long id);
    List<Product> getAllProduct();
    Product addProductForFirstTime(Product product);
    Product deleteById(Long id);
    void updateProduct(Product product);
}
