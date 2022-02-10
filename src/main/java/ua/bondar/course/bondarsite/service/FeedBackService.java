package ua.bondar.course.bondarsite.service;

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
public class FeedBackService {

    private final static Logger logger = LogManager.getLogger(FeedBackService.class);

    @Autowired
    private FeedBackRepositary feedBackRepositary;

    public List<FeedBack> getAllFeedBack(){
        return feedBackRepositary.findAll();
    }

    public FeedBack addNewFeedBack(FeedBack feedBack) {
        feedBack.setDate(new Date());
        logger.info("Cтворено відгук: " + feedBack.getUserOfShop().getUsername() + " feedbackId: " + feedBack.getId());
        return feedBackRepositary.save(feedBack);
    }

    public void deleteById(Long id){
        logger.info("Видалено відгук feedbackId: " + id);
        feedBackRepositary.deleteById(id);
    }
}
