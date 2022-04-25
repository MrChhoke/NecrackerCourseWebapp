package ua.bondar.course.bondarsite.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.Date;


@Entity
@Data
public class FeedBack {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_feedbacks")
    @SequenceGenerator(sequenceName = "id_feedback_sequence", name = "id_feedbacks", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private UserOfShop userOfShop;

    @Size(min = 20, max = 1500, message = "FeedBack must be beetwen 20 and 1500 characters")
    private String feedBackText;

    @Getter(value = AccessLevel.NONE)
    private Date date;

    public String getDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm dd.MM.yyyy");
        return simpleDateFormat.format(date);
    }

}
