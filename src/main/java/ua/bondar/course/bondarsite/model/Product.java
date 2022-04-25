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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_product_items")
    @SequenceGenerator(sequenceName = "id_product_item_sequence", name = "id_product_items", allocationSize = 1)
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
    private String idPhoto;

    private Boolean active = true;

    public String getUrlPhoto(){
        return "https://drive.google.com/uc?export=view&id=" + idPhoto;
    }


}
