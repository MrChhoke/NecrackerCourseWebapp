package ua.bondar.course.bondarsite.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.bondar.course.bondarsite.model.item.CartItem;
import ua.bondar.course.bondarsite.model.item.Product;
import ua.bondar.course.bondarsite.model.item.ProductDecorator;
import ua.bondar.course.bondarsite.model.item.ShoppingCart;
import ua.bondar.course.bondarsite.repo.CardItemRepository;
import ua.bondar.course.bondarsite.repo.ShoppingCartRepository;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final CardItemRepository cardItemRepository;
    private final ProductService productService;
    private final FileService fileService;


    @Autowired
    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository,
                               FileService fileService,
                               CardItemRepository cardItemRepository,
                               ProductService productService) {
        this.fileService = fileService;
        this.shoppingCartRepository = shoppingCartRepository;
        this.cardItemRepository = cardItemRepository;
        this.productService = productService;
    }

    public ShoppingCart addShoppingCartFirstTime(Long id, String sessionToken, int amount){
        ShoppingCart shoppingCart = new ShoppingCart();
        CartItem cartItem = new CartItem();
        cartItem.setDate(new Date());
        cartItem.setAmount(amount);
        cartItem.setProduct(productService.getProductById(id));
        shoppingCart.getItems().add(cartItem);
        shoppingCart.setCompliedOrder(false);
        shoppingCart.setTokenSession(sessionToken);
        log.info("Створено корзину за сесійним токеном: " + sessionToken);
        return shoppingCartRepository.save(shoppingCart);
    }

    public ShoppingCart addToExistingShoppingCart(Long id, String sessionToken, int amount){
        ShoppingCart shoppingCart = shoppingCartRepository.findByTokenSession(sessionToken);
        Product p = productService.getProductById(id);
        for (CartItem item : shoppingCart.getItems()) {
            if (p.getId().equals(item.getProduct().getId())) {
                item.setAmount(item.getAmount() + amount);
                return shoppingCartRepository.saveAndFlush(shoppingCart);
            }
        }
        CartItem cardItem = new CartItem();
        cardItem.setDate(new Date());
        cardItem.setProduct(productService.getProductById(id));
        cardItem.setAmount(amount);
        shoppingCart.getItems().add(cardItem);
        log.info("Додано предмет в корзину за сесійним токеном: " + sessionToken);
        return shoppingCartRepository.saveAndFlush(shoppingCart);

    }

    public ShoppingCart getShoppingCartByTokenSession(String sessionToken) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByTokenSession(sessionToken);
        shoppingCart.getItems()
                    .forEach(cartItem -> cartItem.setProduct(
                            new ProductDecorator(cartItem.getProduct(),fileService,productService)
                    ));
        return shoppingCart;
    }

    public CartItem updateShoppingCartItem(Long id, int amount) {
        CartItem cartItem = cardItemRepository.findById(id).get();
        cartItem.setAmount(amount);
        log.info("Обновлено корзину за id: " + id);
        return cardItemRepository.saveAndFlush(cartItem);
    }

    public ShoppingCart removeCartItemFromShoppingCard(Long id, String sessionToken) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByTokenSession(sessionToken);
        Set<CartItem> items = shoppingCart.getItems();
        CartItem cartItem = null;
        for(CartItem cart : items){
            if(cart.getId().equals(id)){
                cartItem = cart;
            }
        }
        items.remove(cartItem);
        cardItemRepository.delete(cartItem);
        shoppingCart.setItems(items);
        log.info("Видалення з корзини за id: " + id);
        return shoppingCartRepository.save(shoppingCart);
    }

    public void clearShoppingCart(String sessionToken) {
        ShoppingCart sh = shoppingCartRepository.findByTokenSession(sessionToken);
        log.info("Очищення корзини за сесійним токеном: " + sessionToken);
        shoppingCartRepository.delete(sh);
    }

    public ShoppingCart buyShoppingCart(String sessionToken, String location, String buyerName) {
        ShoppingCart sh = shoppingCartRepository.findByTokenSession(sessionToken);
        sh.setLocation(location);
        sh.setActive(true);
        sh.setTokenSession(null);
        sh.setDate(new Date());
        sh.setBuyerName(buyerName);
        if(buyerName != null)
            log.info("Оформлення замовлення клієнтом: " + buyerName);
        else
            log.info("Оформлення замовлення за сесійним токен: " + sessionToken);
        return shoppingCartRepository.saveAndFlush(sh);
    }

    public void acceptOrder(Long id){
        ShoppingCart sh = shoppingCartRepository.findShoppingCartById(id);
        sh.setActive(false);
        sh.setCompliedOrder(true);
        log.info("Обробка замовлення №" + id + " адмінстратором");
        shoppingCartRepository.saveAndFlush(sh);
    }

    public List<ShoppingCart> getShoppingCartsByActive(Boolean active){
        List<ShoppingCart> shoppingCarts = shoppingCartRepository.findShoppingCartsByActive(active);
        for (ShoppingCart shoppingCart: shoppingCarts) {
            shoppingCart.getItems()
                    .forEach(cartItem -> cartItem.setProduct(
                            new ProductDecorator(cartItem.getProduct(),fileService,productService)
                    ));
        }
        return shoppingCarts;
    }

    public List<ShoppingCart> getHistoryOfUser(String username){
        List<ShoppingCart> shoppingCarts = shoppingCartRepository.findShoppingCartByBuyerNameAndCompliedOrder(username,true);
        for (ShoppingCart shoppingCart: shoppingCarts) {
            shoppingCart.getItems()
                    .forEach(cartItem -> cartItem.setProduct(
                            new ProductDecorator(cartItem.getProduct(),fileService,productService)
                    ));
        }
        return shoppingCarts;
    }

    public List<ShoppingCart> getFullHistoryOrderHistory(){
        List<ShoppingCart> shoppingCarts = shoppingCartRepository.findShoppingCartByCompliedOrder(true);
        for (ShoppingCart shoppingCart: shoppingCarts) {
            shoppingCart.getItems()
                    .forEach(cartItem -> cartItem.setProduct(
                            new ProductDecorator(cartItem.getProduct(),fileService,productService)
                    ));
        }
        return shoppingCarts;
    }


    public void deleteTheExecutedOrderById(Long id){
        shoppingCartRepository.deleteById(id);
    }

}
