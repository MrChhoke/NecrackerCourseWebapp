package ua.bondar.course.bondarsite.model;


import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int amount;

    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;

}
