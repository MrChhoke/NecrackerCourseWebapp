package ua.bondar.course.bondarsite.controllers;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ua.bondar.course.bondarsite.model.RegisterForm;
import ua.bondar.course.bondarsite.repo.UserRepo;

@Controller
@Slf4j
public class LoginController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String processUser(RegisterForm form, Model model){
        if(userRepo.existsUserOfShopsByUsername(form.getUsername())){
            model.addAttribute("existUser", true);
            log.warn("Була спроба створити існуючуго юзера! - " + form.getUsername());
            return "registration";
        }
        log.info("Створено юзера: " + form.getUsername());
        userRepo.save(form.toUser(passwordEncoder));
        return "redirect:/login";
    }
}
