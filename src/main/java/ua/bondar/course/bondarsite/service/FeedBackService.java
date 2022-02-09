package ua.bondar.course.bondarsite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.bondar.course.bondarsite.model.FeedBack;
import ua.bondar.course.bondarsite.model.UserOfShop;
import ua.bondar.course.bondarsite.repo.FeedBackRepositary;

import java.util.Date;
import java.util.List;

@Service
public class FeedBackService {

    @Autowired
    private FeedBackRepositary feedBackRepositary;

    public List<FeedBack> getAllFeedBack(){
        return feedBackRepositary.findAll();
    }

    public FeedBack addNewFeedBack(FeedBack feedBack) {
        feedBack.setDate(new Date());
        return feedBackRepositary.save(feedBack);
    }

    public void deleteById(Long id){
        feedBackRepositary.deleteById(id);
    }
}
