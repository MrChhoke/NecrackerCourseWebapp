package ua.bondar.course.bondarsite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.bondar.course.bondarsite.model.Product;
import ua.bondar.course.bondarsite.repo.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product getProductById(Long id){
        return productRepository.findProductById(id);
    }

    public List<Product> getAllProduct(){
        return productRepository.findProductByActive(true);
    }

    public Product addProductForFirstTime(Product product){
        return productRepository.saveAndFlush(product);
    }

    public Product deleteById(Long id){
        Product product = productRepository.findProductById(id);
        product.setActive(false);
        return productRepository.saveAndFlush(product);
    }
}
