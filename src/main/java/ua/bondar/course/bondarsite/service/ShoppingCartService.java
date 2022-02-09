package ua.bondar.course.bondarsite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.bondar.course.bondarsite.dao.DAOProduct;
import ua.bondar.course.bondarsite.model.CartItem;
import ua.bondar.course.bondarsite.model.Product;
import ua.bondar.course.bondarsite.model.ShoppingCart;
import ua.bondar.course.bondarsite.repo.CardItemRepository;
import ua.bondar.course.bondarsite.repo.ShoppingCartRepository;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class ShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private CardItemRepository cardItemRepository;

    @Autowired
    private ProductService productService;


    public ShoppingCart addShoppingCartFirstTime(Long id, String sessionToken, int amount){
        ShoppingCart shoppingCart = new ShoppingCart();
        CartItem cartItem = new CartItem();
        cartItem.setDate(new Date());
        cartItem.setAmount(amount);
        cartItem.setProduct(productService.getProductById(id));
        shoppingCart.getItems().add(cartItem);
        shoppingCart.setCompliedOrder(false);
        shoppingCart.setTokenSession(sessionToken);
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
        return shoppingCartRepository.saveAndFlush(shoppingCart);

    }

    public ShoppingCart getShoppingCartByTokenSession(String sessionToken) {
        return shoppingCartRepository.findByTokenSession(sessionToken);
    }

    public CartItem updateShoppingCartItem(Long id, int amount) {
        CartItem cartItem = cardItemRepository.findById(id).get();
        cartItem.setAmount(amount);
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
        return shoppingCartRepository.save(shoppingCart);
    }

    public void clearShoppingCart(String sessionToken) {
        ShoppingCart sh = shoppingCartRepository.findByTokenSession(sessionToken);
        shoppingCartRepository.delete(sh);
    }

    public ShoppingCart buyShoppingCart(String sessionToken, String location, String buyerName) {
        ShoppingCart sh = shoppingCartRepository.findByTokenSession(sessionToken);
        sh.setLocation(location);
        sh.setActive(true);
        sh.setCompliedOrder(true);
        sh.setTokenSession(null);
        sh.setDate(new Date());
        sh.setBuyerName(buyerName);
        return shoppingCartRepository.saveAndFlush(sh);
    }

    public void acceptOrder(Long id){
        ShoppingCart sh = shoppingCartRepository.findShoppingCartById(id);
        sh.setActive(false);
        shoppingCartRepository.saveAndFlush(sh);
    }

    public List<ShoppingCart> getShoppingCartsByActive(Boolean active){
        return shoppingCartRepository.findShoppingCartsByActive(active);
    }

    public List<ShoppingCart> getHistoryOfUser(String username){
        return shoppingCartRepository.findShoppingCartByBuyerNameAndCompliedOrder(username,true);
    }


    public void deleteTheExecutedOrderById(Long id){
        shoppingCartRepository.deleteById(id);
    }

}
