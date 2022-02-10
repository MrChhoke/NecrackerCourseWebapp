package ua.bondar.course.bondarsite.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ua.bondar.course.bondarsite.model.RegisterForm;
import ua.bondar.course.bondarsite.repo.UserRepo;

@Controller
public class LoginController {

    private static final Logger logger = LogManager.getLogger(LoginController.class);

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
    public String processUser(RegisterForm form){
        /*
        * anom1 - це недоступне ім'я для юзера!
        */
        if(form.getUsername().equals("anom1")){
            logger.error("Спроба створити забороненого юзера!");
            return "registration";
        }

        logger.info("Створено юзера: " + form.getUsername());
        userRepo.save(form.toUser(passwordEncoder));
        return "redirect:/login";
    }
}
