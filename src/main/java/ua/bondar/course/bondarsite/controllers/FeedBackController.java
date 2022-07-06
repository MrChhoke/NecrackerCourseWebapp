package ua.bondar.course.bondarsite.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.bondar.course.bondarsite.model.message.FeedBack;
import ua.bondar.course.bondarsite.model.user.UserOfShop;
import ua.bondar.course.bondarsite.service.FeedBackService;

import javax.validation.Valid;

@Controller
public class FeedBackController {

    private final FeedBackService feedBackService;

    @Autowired
    public FeedBackController(FeedBackService feedBackService) {
        this.feedBackService = feedBackService;
    }

    @GetMapping("/feedback")
    public String feedBackPage(@AuthenticationPrincipal UserOfShop user,
                               @ModelAttribute("feedbackForm") @Valid FeedBack feedBack,
                               Model model){

        model.addAttribute("user", user);
        feedBack.setUserOfShop(user);
        model.addAttribute("feedBackList", feedBackService.getAllFeedBack());
        return "feedback";
    }

    @PostMapping("/feedback")
    public String setFeedBack(@AuthenticationPrincipal UserOfShop user,
                              @ModelAttribute("feedbackForm") @Valid FeedBack feedBack,
                              BindingResult bindingResult,
                              Model model){

        model.addAttribute("user", user);

        if(bindingResult.hasErrors()){
            model.addAttribute("feedBackList", feedBackService.getAllFeedBack());
            return "feedback";
        }

        feedBack.setUserOfShop(user);
        feedBackService.addNewFeedBack(feedBack);
        return "redirect:/feedback";
    }

    @PreAuthorize(value = "hasAnyAuthority('ADMIN')")
    @DeleteMapping("/feedback/{id}")
    public String deleteFeedBack(@PathVariable(name = "id") Long id){
        feedBackService.deleteById(id);
        return "redirect:/feedback";
    }
}
