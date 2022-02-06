package ua.bondar.course.bondarsite.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ua.bondar.course.bondarsite.model.RegisterForm;
import ua.bondar.course.bondarsite.repo.UserRepo;

@Controller
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
    public String processUser(RegisterForm form){
        userRepo.save(form.toUser(passwordEncoder));

        return "redirect:/login";
    }
}
