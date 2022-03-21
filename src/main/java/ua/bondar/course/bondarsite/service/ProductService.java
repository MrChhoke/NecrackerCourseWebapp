package ua.bondar.course.bondarsite.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.bondar.course.bondarsite.model.CategoryProduct;
import ua.bondar.course.bondarsite.model.Product;
import ua.bondar.course.bondarsite.repo.ProductRepository;

import java.util.List;

@Service
@Slf4j
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
        log.info("Був доданий продукт: " + product.getName());
        return productRepository.saveAndFlush(product);
    }

    public List<Product> getAllProductByCategory(CategoryProduct categoryProduct){
        return productRepository.findProductByCategoryAndActive(categoryProduct,true);
    }

    public Product deleteById(Long id){
        Product product = productRepository.findProductById(id);
        product.setActive(false);
        log.info("Замовлення №" + id + " було видалено адмністратором");
        return productRepository.saveAndFlush(product);
    }
}
