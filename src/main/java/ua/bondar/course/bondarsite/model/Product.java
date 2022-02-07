package ua.bondar.course.bondarsite.model;


import lombok.Data;
import javax.persistence.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Поле не может быть пустым!")
    @Size(min = 3, max = 30, message = "Имя должно быть между 3 и 30 символами!")
    private String name;

    @Size(min = 10, max = 300, message = "Описание должно быть между 10 и 300 символами!")
    private String description;

    @Enumerated(EnumType.STRING)
    private CategoryProduct category;

    @Min(message = "Цена должна быть больше 1", value = 1)
    private double price;

    @Column(columnDefinition = "TEXT")
    private String nameImg;

}
