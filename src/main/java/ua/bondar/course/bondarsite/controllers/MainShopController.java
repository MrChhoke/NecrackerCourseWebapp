package ua.bondar.course.bondarsite.controllers;




import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.bondar.course.bondarsite.dao.DAOGoods;
import ua.bondar.course.bondarsite.model.CategoryProduct;
import ua.bondar.course.bondarsite.model.Product;


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


    private final DAOGoods daoGoods = new DAOGoods();

    @GetMapping("/")
    public String index(Model model){

        List<List<Product>> goodsForModal = positionForModel();

        model.addAttribute("goodsForModal", goodsForModal);
        return "index";
    }

    @GetMapping("/{id}")
    public String product(Model model, @PathVariable(name = "id") int id){
        List<Product> goods = daoGoods.getGoods();
        model.addAttribute("product", daoGoods.getProductById(id));
        return "product";
    }

    @GetMapping("/adminpanel")
    public String adminPanel(@ModelAttribute("formproduct") Product product, Model model){
        List<List<Product>> goodsForModal = positionForModel();
        List<String> allCategory = CategoryProduct.getAllCategoryProductInString();
        model.addAttribute("goodsForModal", goodsForModal);
        model.addAttribute("allCategory", allCategory);
        return "adminPanel";
    }

    @DeleteMapping("/adminpanel/{id}")
    public String deleteInAdminPanel(Model model, @PathVariable(name = "id") int id){
        daoGoods.deleteProductById(id);
        return "redirect:/adminpanel";
    }


    @PostMapping("/adminpanel")
    public String createProduct(@ModelAttribute("formproduct") @Valid Product product,
                                BindingResult bindingResult, Model model,
                                @RequestParam(value = "file", required = false)MultipartFile file,
                                @RequestParam(value = "category", required = false) String category) throws IOException {

            if(bindingResult.hasErrors()){
                List<List<Product>> goodsForModal = positionForModel();
                List<String> allCategory = CategoryProduct.getAllCategoryProductInString();
                model.addAttribute("goodsForModal", goodsForModal);
                model.addAttribute("allCategory", allCategory);
                return "adminPanel";
            }
            if (category == null) category = CategoryProduct.getAllCategoryProductInString().get(1);
            product.setCategory(CategoryProduct.getCategoryProduct(category));
            product.setId(0);


            if(file.isEmpty()){
                File fileChange = new File("C:\\Users\\vladb\\IdeaProjects\\bondarSite\\src\\main\\resources\\static\\img\\defaulIcon.png");
                try(FileInputStream fileInputStream = new FileInputStream(fileChange)){
                    product.setNameImg(Base64.getEncoder().encodeToString(fileInputStream.readAllBytes()));
                }
            }else{
                product.setNameImg(Base64.getEncoder().encodeToString(file.getBytes()));
            }
            daoGoods.setProduct(product);
        return "redirect:/adminpanel";
    }

    private List<List<Product>> positionForModel(){
        List<Product> goods =  daoGoods.getGoods();
        List<List<Product>> goodsForModal = new ArrayList<>();
        int index = 0;
        for(int i = 0; i < goods.size(); i += COUNT_GOODS_IN_PAGE){
            goodsForModal.add(new ArrayList<Product>());
            for(int j = 0; j < COUNT_GOODS_IN_PAGE && i+j < goods.size(); j++){
                goodsForModal.get(index).add(goods.get(i+j));
            }
            index++;
        }
        return goodsForModal;
    }
}
