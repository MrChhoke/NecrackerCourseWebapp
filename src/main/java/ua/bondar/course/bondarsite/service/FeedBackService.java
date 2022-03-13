package ua.bondar.course.bondarsite.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.bondar.course.bondarsite.model.FeedBack;
import ua.bondar.course.bondarsite.model.UserOfShop;
import ua.bondar.course.bondarsite.repo.FeedBackRepositary;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class FeedBackService {

    @Autowired
    private FeedBackRepositary feedBackRepositary;

    public List<FeedBack> getAllFeedBack(){
        return feedBackRepositary.findAll();
    }

    public FeedBack addNewFeedBack(FeedBack feedBack) {
        feedBack.setDate(new Date());
        log.info("Cтворено відгук: " + feedBack.getUserOfShop().getUsername() + " feedbackId: " + feedBack.getId());
        return feedBackRepositary.save(feedBack);
    }

    public void deleteById(Long id){
        log.info("Видалено відгук feedbackId: " + id);
        feedBackRepositary.deleteById(id);
    }
}
