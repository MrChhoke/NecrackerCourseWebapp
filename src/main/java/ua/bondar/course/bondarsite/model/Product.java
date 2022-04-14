package ua.bondar.course.bondarsite.model;


import lombok.Data;
import javax.persistence.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, max = 30, message = "Ім'я має містити від 3 до 30 символів")
    private String name;

    @Size(min = 10, max = 300, message = "Опис має містити від 10 до 300 символів")
    private String description;

    @Enumerated(EnumType.STRING)
    private CategoryProduct category;

    @Min(message = "Ціна повинна бути більше 1", value = 1)
    private double price;

    @Column(columnDefinition = "TEXT")
    private String nameImg;


    private Boolean active = true;
}
