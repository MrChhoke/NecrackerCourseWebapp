package ua.bondar.course.bondarsite.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ua.bondar.course.bondarsite.model.item.Product;
import ua.bondar.course.bondarsite.model.item.ProductDecorator;
import ua.bondar.course.bondarsite.repo.ProductRepository;
import ua.bondar.course.bondarsite.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Primary
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final FileServiceGoogle fileServiceGoogle;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              FileServiceGoogle fileServiceGoogle) {
        this.productRepository = productRepository;
        this.fileServiceGoogle = fileServiceGoogle;
    }

    public Product getProductById(Long id) {
        return new ProductDecorator(productRepository.findProductById(id), fileServiceGoogle, this).getProduct();
    }

    public List<Product> getAllProduct() {
        return productRepository.findProductByActive(true)
                .stream()
                .map(item -> new ProductDecorator(item, fileServiceGoogle, this))
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
        return new ProductDecorator(productRepository.saveAndFlush(product), fileServiceGoogle, this);
    }

    public void updateProduct(Product product) {
        log.info("Замовлення №" + product.getId() + " було оновлено адмністратором");
        productRepository.saveAndFlush(product);
    }
}
