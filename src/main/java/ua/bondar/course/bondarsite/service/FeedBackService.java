package ua.bondar.course.bondarsite.service;

import ua.bondar.course.bondarsite.model.item.CartItem;
import ua.bondar.course.bondarsite.model.item.ShoppingCart;
import ua.bondar.course.bondarsite.model.message.FeedBack;

import java.util.List;

public interface FeedBackService {
    List<FeedBack> getAllFeedBack();
    FeedBack addNewFeedBack(FeedBack feedBack);
    void deleteById(Long id);
}
