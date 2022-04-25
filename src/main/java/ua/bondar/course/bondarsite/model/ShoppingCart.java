package ua.bondar.course.bondarsite.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "shopping_cart")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_shopping_carts")
    @SequenceGenerator(sequenceName = "id_shopping_cart_sequence", name = "id_shopping_carts", allocationSize = 1)
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date date;

    @Transient // ignore this field
    private Double totalPrice;

    @Transient // ignore this field
    private int totalNumber;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "shopping_cart_id")
    private Set<CartItem> items;

    @Column(name = "token_session")
    private String tokenSession;

    @Column(name = "location")
    private String location;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "buyer_name")
    private String buyerName;

    @Column(name = "complied_order")
    private Boolean compliedOrder;

    public ShoppingCart(){
        items = new HashSet<>();
    }


    public Double getTotalPrice() {
        Double sum = 0D;
        for(CartItem item : items){
            sum += item.getAmount() * item.getProduct().getPrice();
        }
        return sum;
    }

    public int getTotalNumber() {
        return items.size();
    }
}
