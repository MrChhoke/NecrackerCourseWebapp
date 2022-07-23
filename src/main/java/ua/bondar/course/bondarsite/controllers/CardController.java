package ua.bondar.course.bondarsite.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.bondar.course.bondarsite.model.item.ShoppingCart;
import ua.bondar.course.bondarsite.model.user.UserOfShop;
import ua.bondar.course.bondarsite.service.ShoppingCartService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
public class CardController {


    private final ShoppingCartService shoppingCartService;

    @Autowired
    public CardController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping("/addToCart/{id}")
    public String addToCart(HttpSession httpSession,
                            @RequestParam("quantity") int quantity,
                            @PathVariable(name = "id") Long id){


        String tokenSession = (String) httpSession.getAttribute("sessionToken");
        if(tokenSession == null){
            tokenSession = UUID.randomUUID().toString();
            httpSession.setAttribute("sessionToken", tokenSession);
            shoppingCartService.addShoppingCartFirstTime(id,tokenSession, quantity);
            return "redirect:/";
        }

        shoppingCartService.addToExistingShoppingCart(id,tokenSession,quantity);
        return "redirect:/";
    }

    @GetMapping("/shoppingCart")
    public String shoppingCart(@AuthenticationPrincipal UserOfShop user,
                               HttpServletRequest request,
                               Model model){

        model.addAttribute("user", user);
        String sessionToken = (String) request.getSession(true).getAttribute("sessionToken");
        if(sessionToken == null){
            model.addAttribute("shoppingCart", new ShoppingCart());
        }else{
            ShoppingCart shoppingCart = shoppingCartService.getShoppingCartByTokenSession(sessionToken);
            model.addAttribute("shoppingCart", shoppingCart);
        }

        return "shoppingCart";
    }

    @PostMapping("/updateShoppingCart")
    public String updateCartItem(@AuthenticationPrincipal UserOfShop user,
                                 Model model,
                                 @RequestParam("item_id") Long id,
                                 @RequestParam("quantity2") int quantity){

        model.addAttribute("user", user);
        shoppingCartService.updateShoppingCartItem(id, quantity);
        return "redirect:/shoppingCart";
    }

    @GetMapping("/removeItem/{id}")
    public String removeItem(@PathVariable("id") Long id,
                             HttpServletRequest request){

        String sessionToken = (String) request.getSession(false).getAttribute("sessionToken");
        shoppingCartService.removeCartItemFromShoppingCard(id,sessionToken);
        return "redirect:/shoppingCart";
    }

    @GetMapping("/clearShoppingCart")
    public String clearShoppingCart(HttpServletRequest request){
        String sessionToken = (String) request.getSession(false).getAttribute("sessionToken");
        request.getSession(false).removeAttribute("sessionToken");
        shoppingCartService.clearShoppingCart(sessionToken);
        return "redirect:/shoppingCart";
    }

    @GetMapping("/buyShoppingCart")
    public String buyShoppingCart(@AuthenticationPrincipal UserOfShop user,
                                  Model model,
                                  HttpServletRequest request,
                                  @RequestParam("location") String location){

        model.addAttribute("user", user);
        String sessionToken = (String) request.getSession(false).getAttribute("sessionToken");
        request.getSession(false).removeAttribute("sessionToken");
        if(user != null) {
            shoppingCartService.buyShoppingCart(sessionToken, location, user.getUsername());
        } else {
            shoppingCartService.buyShoppingCart(sessionToken, location, null);
        }
        return "redirect:/shoppingCart";
    }
}
