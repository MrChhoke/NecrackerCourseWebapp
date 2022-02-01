package ua.bondar.course.bondarsite.model;


import org.springframework.stereotype.Component;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Objects;

@Component
public class Product {

    private int id;
    @NotEmpty(message = "Поле не может быть пустым!")
    @Size(min = 3, max = 30, message = "Имя должно быть между 3 и 30 символами!")
    private String name;
    @Size(min = 10, max = 300, message = "Описание должно быть между 10 и 300 символами!")
    private String description;
    private CategoryProduct category;
    @Min(message = "Цена должна быть больше 1", value = 1)
    private double price;
    private String nameImg;


    public Product(){}

    public Product(int id, String name, String description, CategoryProduct category, double price, String nameImg) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.nameImg = nameImg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CategoryProduct getCategory() {
        return category;
    }

    public void setCategory(CategoryProduct category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getNameImg() {
        return nameImg;
    }

    public void setNameImg(String nameImg) {
        this.nameImg = nameImg;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", price=" + price +
                ", nameImg='" + nameImg + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && Double.compare(product.price, price) == 0 &&
                name.equals(product.name) && description.equals(product.description) &&
                category == product.category && nameImg.equals(product.nameImg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, category, price, nameImg);
    }
}
