package ua.bondar.course.bondarsite.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ua.bondar.course.bondarsite.model.message.FeedBack;
import ua.bondar.course.bondarsite.repo.FeedBackRepositary;
import ua.bondar.course.bondarsite.service.FeedBackService;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
@Primary
public class FeedBackServiceImpl implements FeedBackService {

    private final FeedBackRepositary feedBackRepositary;

    @Autowired
    public FeedBackServiceImpl(FeedBackRepositary feedBackRepositary) {
        this.feedBackRepositary = feedBackRepositary;
    }

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
