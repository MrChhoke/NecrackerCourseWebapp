package ua.bondar.course.bondarsite.model.item;


import lombok.*;

import javax.persistence.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_product_items")
    @SequenceGenerator(sequenceName = "id_product_item_sequence", name = "id_product_items", allocationSize = 1)
    protected Long id;

    @Size(min = 3, max = 30, message = "Ім'я має містити від 3 до 30 символів")
    protected String name;

    @Size(min = 10, max = 300, message = "Опис має містити від 10 до 300 символів")
    protected String description;

    @Enumerated(EnumType.STRING)
    protected CategoryProduct category;

    @Min(message = "Ціна повинна бути більше 1", value = 1)
    protected double price;

    @Column(name = "id_photo", columnDefinition = "TEXT")
    protected String idPhoto;

    protected Boolean active = true;

    @Transient
    private String photoUrl;

    public String getUrlPhoto() {
        return photoUrl;
    }
}
