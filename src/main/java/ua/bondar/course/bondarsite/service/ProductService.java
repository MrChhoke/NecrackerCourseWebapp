package ua.bondar.course.bondarsite.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.bondar.course.bondarsite.model.item.CategoryProduct;
import ua.bondar.course.bondarsite.model.item.Product;
import ua.bondar.course.bondarsite.model.item.ProductDecorator;
import ua.bondar.course.bondarsite.repo.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final FileService fileService;

    @Autowired
    public ProductService(ProductRepository productRepository,
                          FileService fileService) {
        this.productRepository = productRepository;
        this.fileService = fileService;
    }

    public Product getProductById(Long id) {
        return new ProductDecorator(productRepository.findProductById(id), fileService, this);
    }

    public List<Product> getAllProduct() {
        return productRepository.findProductByActive(true)
                .stream()
                .map(item -> new ProductDecorator(item, fileService, this))
                .collect(Collectors.toList());
    }

    public Product addProductForFirstTime(Product product) {
        log.info("Був доданий продукт: " + product.getName());
        return productRepository.saveAndFlush(product);
    }

    public Product deleteById(Long id) {
        Product product = productRepository.findProductById(id);
        product.setActive(false);
        log.info("Замовлення №" + id + " було видалено адмністратором");
        return new ProductDecorator(productRepository.saveAndFlush(product), fileService, this);
    }

    public void updateProduct(Product product) {
        log.info("Замовлення №" + product.getId() + " було оновлено адмністратором");
        productRepository.saveAndFlush(product);
    }
}
