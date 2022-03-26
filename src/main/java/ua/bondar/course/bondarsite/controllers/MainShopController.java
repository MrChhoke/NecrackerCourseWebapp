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


@Controller
public class MainShopController {

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String mainPage(@AuthenticationPrincipal UserOfShop user,
                        Model model,@RequestParam(value = "category", required = false) String category){

        List<Product> goodsForModal = cardItemsForModal(category);
        List<String> allCategory = CategoryProduct.getAllCategoryProductInString();
        model.addAttribute("products", goodsForModal);
        model.addAttribute("allCategory", allCategory);

        if(user != null){
            model.addAttribute("user", user.getUsername());
            return "index";
        }
        model.addAttribute("user", "anom1");
        return "index";
    }

    @PostMapping("/")
    public String mainPageSortByCategory(@AuthenticationPrincipal UserOfShop user,
                                         Model model,
                                         @RequestParam(value = "category", required = false) String category){

        List<Product> goodsForModal = cardItemsForModal(category);
        List<String> allCategory = CategoryProduct.getAllCategoryProductInString();
        model.addAttribute("products", goodsForModal);
        model.addAttribute("allCategory", allCategory);

        if(user != null){
            model.addAttribute("user", user.getUsername());
            return "index";
        }
        model.addAttribute("user", "anom1");
        return "index";
    }

    @GetMapping("/{id}")
    public String product(@AuthenticationPrincipal UserOfShop user,
                          Model model, @PathVariable(name = "id") Long id){
        model.addAttribute("product", productService.getProductById(id));

        if(user != null){
            model.addAttribute("user", user.getUsername());
            return "product";
        }
        model.addAttribute("user", "anom1");
        return "product";
    }


    private List<Product> cardItemsForModal(String category){
        CategoryProduct categoryProduct = CategoryProduct.getCategoryProduct(category);
        List<Product> goods;
        if(categoryProduct == null)
            goods =  productService.getAllProduct();
        else{
            goods = productService.getAllProductByCategory(categoryProduct);
        }
        return goods;
    }
}
