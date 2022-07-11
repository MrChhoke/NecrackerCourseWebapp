package ua.bondar.course.bondarsite.model.item;

import ua.bondar.course.bondarsite.service.FileService;
import ua.bondar.course.bondarsite.service.ProductService;

import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class ProductDecorator extends Product {

    private final Product product;
    private final ProductService productService;
    private final FileService fileService;

    public ProductDecorator(Product product, FileService fileService, ProductService productService){
        this.product = product;
        this.productService = productService;
        this.fileService = fileService;
        if(product.getIdPhoto() == null) {
            product.setIdPhoto(null);
            product.setPhotoUrl("/static/img/defaulIcon.png");
        }else if(!fileService.isFileExist(product.getIdPhoto())){
            product.setIdPhoto(null);
            productService.updateProduct(product);
            product.setPhotoUrl("/static/img/defaulIcon.png");
        }
        else{
            product.setPhotoUrl("https://drive.google.com/uc?export=view&id=" + product.getIdPhoto());
        }
    }

    @Override
    public Long getId() {
        return product.getId();
    }

    @Override
    public String getName() {
        return product.getName();
    }

    @Override
    public String getDescription() {
        return product.getDescription();
    }

    @Override
    public CategoryProduct getCategory() {
        return product.getCategory();
    }

    @Override
    public double getPrice() {
        return product.getPrice();
    }

    @Override
    public String getIdPhoto() {
        return product.getIdPhoto();
    }

    @Override
    public Boolean getActive() {
        return product.getActive();
    }

    @Override
    public void setId(Long id) {
        product.setId(id);
    }

    @Override
    public void setName(@Size(min = 3, max = 30, message = "Ім'я має містити від 3 до 30 символів") String name) {
        product.setName(name);
    }

    @Override
    public void setDescription(@Size(min = 10, max = 300, message = "Опис має містити від 10 до 300 символів") String description) {
        product.setDescription(description);
    }

    @Override
    public void setCategory(CategoryProduct category) {
        product.setCategory(category);
    }

    @Override
    public void setPrice(@Min(message = "Ціна повинна бути більше 1", value = 1) double price) {
        product.setPrice(price);
    }

    @Override
    public void setIdPhoto(String idPhoto) {
        product.setIdPhoto(idPhoto);
    }

    @Override
    public void setActive(Boolean active) {
        product.setActive(active);
    }

    public String getUrlPhoto() {
        return product.getPhotoUrl();
    }

    public Product getProduct() {
        return product;
    }
}
