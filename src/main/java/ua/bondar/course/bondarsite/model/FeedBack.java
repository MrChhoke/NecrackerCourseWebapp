package ua.bondar.course.bondarsite.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;


@Entity
@Data
public class FeedBack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private UserOfShop userOfShop;

    @Size(min = 20, max = 1500, message = "FeedBack must be beetwen 20 and 1500 characters")
    private String feedBackText;

    private Date date;

}
