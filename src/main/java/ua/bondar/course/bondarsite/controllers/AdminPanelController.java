package ua.bondar.course.bondarsite.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
public class AdminPanelController {

    private final static int COUNT_GOODS_IN_PAGE = 5;

    private static final Logger logger = LogManager.getLogger(AdminPanelController.class);

    @Autowired
    private ProductService productService;

    @PreAuthorize(value = "hasAnyAuthority('ADMIN')")
    @GetMapping("/adminpanel")
    public String adminPanel(@AuthenticationPrincipal UserOfShop user,
                             @ModelAttribute("formproduct") Product product, Model model){
        List<List<Product>> goodsForModal = positionForModel();
        List<String> allCategory = CategoryProduct.getAllCategoryProductInString();
        model.addAttribute("goodsForModal", goodsForModal);
        model.addAttribute("allCategory", allCategory);

        if(user != null){
            model.addAttribute("user", user.getUsername());
            return "adminPanel";
        }
        model.addAttribute("user", "anom1");
        return "adminPanel";
    }

    @PreAuthorize(value = "hasAnyAuthority('ADMIN')")
    @DeleteMapping("/adminpanel/{id}")
    public String deleteInAdminPanel(Model model, @PathVariable(name = "id") Long id){
        productService.deleteById(id);
        return "redirect:/adminpanel";
    }

    @PreAuthorize(value = "hasAnyAuthority('ADMIN')")
    @PostMapping("/adminpanel")
    public String createProduct(@AuthenticationPrincipal UserOfShop user,
                                @ModelAttribute("formproduct") @Valid Product product,
                                BindingResult bindingResult, Model model,
                                @RequestParam(value = "file", required = false) MultipartFile file,
                                @RequestParam(value = "category", required = false) String category) throws IOException {

        model.addAttribute("user", user.getUsername());

        if(bindingResult.hasErrors()){
            List<List<Product>> goodsForModal = positionForModel();
            List<String> allCategory = CategoryProduct.getAllCategoryProductInString();
            model.addAttribute("goodsForModal", goodsForModal);
            model.addAttribute("allCategory", allCategory);
            return "adminPanel";
        }
        if (category == null) category = CategoryProduct.getAllCategoryProductInString().get(1);
        product.setCategory(CategoryProduct.getCategoryProduct(category));

        if(file.isEmpty()){
            File fileChange = new File("src\\main\\resources\\static\\img\\defaulIcon.png");
            try(FileInputStream fileInputStream = new FileInputStream(fileChange)){
                product.setNameImg(Base64.getEncoder().encodeToString(fileInputStream.readAllBytes()));
            }
        }else{
            product.setNameImg(Base64.getEncoder().encodeToString(file.getBytes()));
        }
        productService.addProductForFirstTime(product);
        return "redirect:/adminpanel";
    }

    private List<List<Product>> positionForModel(){
        List<Product> goods =  productService.getAllProduct();
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
