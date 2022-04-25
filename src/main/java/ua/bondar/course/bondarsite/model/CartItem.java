package ua.bondar.course.bondarsite.model;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "cart_item")
public class CartItem {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_cart_items")
    @SequenceGenerator(sequenceName = "id_cart_item_sequence", name = "id_cart_items", allocationSize = 1)
    private Long id;

    @Column(name = "amount")
    private int amount;

    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date date;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;
}
