package ua.bondar.course.bondarsite.controllers;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.bondar.course.bondarsite.dao.DAOProduct;
import ua.bondar.course.bondarsite.model.CategoryProduct;
import ua.bondar.course.bondarsite.model.Product;
import ua.bondar.course.bondarsite.model.UserOfShop;
import ua.bondar.course.bondarsite.service.ProductService;


import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


@Controller
public class MainShopController {

    private final static int COUNT_GOODS_IN_PAGE = 5;

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String index(@AuthenticationPrincipal UserOfShop user,
                        Model model,@RequestParam(value = "category", required = false) String category){

        List<List<Product>> goodsForModal = positionForModel(category);
        List<String> allCategory = CategoryProduct.getAllCategoryProductInString();
        model.addAttribute("goodsForModal", goodsForModal);
        model.addAttribute("allCategory", allCategory);

        if(user != null){
            model.addAttribute("user", user.getUsername());
            return "index";
        }
        model.addAttribute("user", "anom1");
        return "index";
    }

    @PostMapping("/")
    public String indexSortByCategory(@AuthenticationPrincipal UserOfShop user,
                        Model model,
                        @RequestParam(value = "category", required = false) String category){

        List<List<Product>> goodsForModal = positionForModel(category);
        List<String> allCategory = CategoryProduct.getAllCategoryProductInString();
        model.addAttribute("goodsForModal", goodsForModal);
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


    private List<List<Product>> positionForModel(String category){

        CategoryProduct categoryProduct = CategoryProduct.getCategoryProduct(category);

        List<Product> goods = null;

        if(categoryProduct == null)
            goods =  productService.getAllProduct();
        else{
            goods = productService.getAllProductByCategory(categoryProduct);
        }
        List<List<Product>> goodsForModal = new ArrayList<>();
        int index = 0;
        for(int i = 0; i < goods.size(); i += COUNT_GOODS_IN_PAGE){
            goodsForModal.add(new ArrayList<>());
            for(int j = 0; j < COUNT_GOODS_IN_PAGE && i+j < goods.size(); j++){
                goodsForModal.get(index).add(goods.get(i+j));
            }
            index++;
        }
        return goodsForModal;
    }
}
