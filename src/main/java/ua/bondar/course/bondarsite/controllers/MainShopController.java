package ua.bondar.course.bondarsite.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.bondar.course.bondarsite.model.CategoryProduct;
import ua.bondar.course.bondarsite.model.Product;
import ua.bondar.course.bondarsite.model.UserOfShop;
import ua.bondar.course.bondarsite.service.ProductService;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class MainShopController {

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String mainPage(
            @AuthenticationPrincipal UserOfShop user,
            Model model,
            @RequestParam(value = "search", required = false) String nameProduct
    ) {

        List<Product> goodsForModal =
                nameProduct == null ? productService.getAllProduct() :
                        productService.getAllProduct().
                                stream().
                                filter(product -> product.getName().contains(nameProduct)).
                                collect(Collectors.toList());
        model.addAttribute("products", goodsForModal);

        model.addAttribute("user", user);
        return "index";
    }

    @GetMapping("/{id}")
    public String product(
            @AuthenticationPrincipal UserOfShop user,
            Model model,
            @PathVariable(name = "id") Long id
    ) {
        model.addAttribute("product", productService.getProductById(id));

        model.addAttribute("user", user);
        return "product";
    }
}
