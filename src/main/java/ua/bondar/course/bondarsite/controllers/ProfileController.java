package ua.bondar.course.bondarsite.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ua.bondar.course.bondarsite.model.Role;
import ua.bondar.course.bondarsite.model.UserOfShop;
import ua.bondar.course.bondarsite.service.ShoppingCartService;

@Controller
public class ProfileController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal UserOfShop user, Model model){
        model.addAttribute("user", user);
        model.addAttribute("allListProduct", shoppingCartService.getShoppingCartsByActive(true));
        model.addAttribute("historyOrder", shoppingCartService.getHistoryOfUser(user.getUsername()));
        if(user.getRoles().stream().anyMatch(role -> role == Role.ADMIN))
            model.addAttribute("fullHistoryOrder", shoppingCartService.getFullHistoryOrderHistory());
        return "profile";
    }

    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @DeleteMapping("/profile/doneOrder/{id}")
    public String doneOrder(@PathVariable(name = "id") long id){
        shoppingCartService.acceptOrder(id);
        return "redirect:/profile";
    }
}
