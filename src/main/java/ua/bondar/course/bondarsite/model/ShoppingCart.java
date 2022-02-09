package ua.bondar.course.bondarsite.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date date;

    @Transient
    private Double totalPrice;

    @Transient
    private int totalNumber;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<CartItem> items;

    private String tokenSession;

    private String location;

    private Boolean active;

    private String buyerName;

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
