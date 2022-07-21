package ua.bondar.course.bondarsite.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.bondar.course.bondarsite.model.item.CategoryProduct;
import ua.bondar.course.bondarsite.model.item.Product;
import ua.bondar.course.bondarsite.model.item.ProductDecorator;
import ua.bondar.course.bondarsite.model.user.UserOfShop;
import ua.bondar.course.bondarsite.service.FileService;
import ua.bondar.course.bondarsite.service.ProductService;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class MainShopController {

    private final ProductService productService;
    private final FileService fileService;

    @Autowired
    public MainShopController(ProductService productService,
                              FileService fileService) {
        this.productService = productService;
        this.fileService = fileService;
    }

    @GetMapping("/")
    public String mainPage(@AuthenticationPrincipal UserOfShop user,
                            Model model,
                            @RequestParam(value = "search", required = false) String nameProduct) {

        List<Product> goodsForModal =
                nameProduct == null ? productService.getAllProduct()
                                            .stream()
                                            .sorted((Comparator.comparing(Product::getId)))
                                            .collect(Collectors.toList())  :
                                      productService.getAllProduct()
                                            .stream()
                                            .filter(product -> product.getName().contains(nameProduct))
                                            .sorted((Comparator.comparing(Product::getId)))
                                            .collect(Collectors.toList());
        model.addAttribute("products", goodsForModal);
        model.addAttribute("user", user);
        return "index";
    }

    @GetMapping("/{id}")
    public String product(@AuthenticationPrincipal UserOfShop user,
                          @PathVariable(name = "id") Long id,
                          Model model) {
        model.addAttribute("product", productService.getProductById(id));
        List<String> allCategory = Arrays.stream(CategoryProduct.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        model.addAttribute("allCategory", allCategory);
        model.addAttribute("user", user);
        return "product";
    }
}
