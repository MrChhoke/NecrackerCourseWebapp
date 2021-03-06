package ua.bondar.course.bondarsite.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Currency {
    private String name;
    private double buyPriceUAH;
    private double soldPriceUAH;
}