package ua.bondar.course.bondarsite.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.bondar.course.bondarsite.model.item.CategoryProduct;
import ua.bondar.course.bondarsite.model.item.Product;
import ua.bondar.course.bondarsite.model.user.UserOfShop;
import ua.bondar.course.bondarsite.service.FileService;
import ua.bondar.course.bondarsite.service.ProductService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
public class AdminPanelController {

    private final ProductService productService;
    private final FileService fileService;

    @Autowired
    public AdminPanelController(ProductService productService, FileService fileService) {
        this.productService = productService;
        this.fileService = fileService;
    }

    @PreAuthorize(value = "hasAnyAuthority('ADMIN')")
    @GetMapping("/adminpanel")
    public String adminPanel(@AuthenticationPrincipal UserOfShop user,
                             @ModelAttribute("formproduct") Product product,
                             Model model) {
        List<String> allCategory = Arrays.stream(CategoryProduct.values())
                                          .map(Enum::name)
                                          .collect(Collectors.toList());
        model.addAttribute("allCategory", allCategory);
        model.addAttribute("user", user);
        model.addAttribute("isFileServiceAccess", fileService.isFileServiceAccess());
        return "adminPanel";
    }

    @PreAuthorize(value = "hasAnyAuthority('ADMIN')")
    @PutMapping("/adminpanel/{id}")
    public String updateProduct(@AuthenticationPrincipal UserOfShop user,
                                HttpServletResponse response,
                                @ModelAttribute("productValid") @Valid Product product, BindingResult bindingResult,Model model,
                                @RequestParam(value = "file", required = false) MultipartFile file,
                                @RequestParam(value = "category", required = false) String category,
                                @PathVariable(value = "id") Long id) {

        model.addAttribute("user", user);

        if(!fileService.isFileServiceAccess()){
            return "redirect:/";
        }

        if (bindingResult.hasErrors()) {
            List<String> allCategory = Arrays.stream(CategoryProduct.values())
                    .map(Enum::name)
                    .collect(Collectors.toList());
            response.setStatus(400);
            model.addAttribute("allCategory", allCategory);
            model.addAttribute("product", productService.getProductById(id));
            return "product";
        }
        uploadPhoto(file,product);
        productService.updateProduct(product);
        return "redirect:/" + product.getId();
    }

    @PreAuthorize(value = "hasAnyAuthority('ADMIN')")
    @DeleteMapping("/adminpanel/{id}")
    public String deleteInAdminPanel(Model model,
                                     @PathVariable(name = "id") Long id) {
        productService.deleteById(id);
        return "redirect:/adminpanel";
    }

    @PreAuthorize(value = "hasAnyAuthority('ADMIN')")
    @PostMapping("/adminpanel")
    public String createProduct(@AuthenticationPrincipal UserOfShop user,
                                @ModelAttribute("formproduct") @Valid Product product,
                                BindingResult bindingResult, Model model,
                                @RequestParam(value = "file", required = false) MultipartFile file,
                                @RequestParam(value = "category", required = false) String category) {

        model.addAttribute("user", user);

        if(!fileService.isFileServiceAccess()){
            return "redirect:/adminpanel";
        }

        if (bindingResult.hasErrors()) {
            List<String> allCategory = Arrays.stream(CategoryProduct.values())
                                              .map(Enum::name)
                                              .collect(Collectors.toList());
            model.addAttribute("allCategory", allCategory);
            return "adminPanel";
        }
        if (category == null) {
            category = CategoryProduct.Desktop.name();
        }

        product.setCategory(CategoryProduct.valueOf(category));
        uploadPhoto(file,product);
        productService.addProductForFirstTime(product);
        return "redirect:/adminpanel";
    }

    private void uploadPhoto(MultipartFile file,
                             Product product){
        if (file.isEmpty()) {
            File fileChange = new File(Paths.get("src/main/resources/static/img/defaulIcon.png").toAbsolutePath().toString());
            product.setIdPhoto(fileService.uploadPhoto(fileChange));
        } else {
            product.setIdPhoto(fileService.uploadPhoto(file));
        }
    }
}
