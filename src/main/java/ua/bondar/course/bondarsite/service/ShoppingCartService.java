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
import java.util.Set;

@Service
public class ShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private CardItemRepository cardItemRepository;

    @Autowired
    private DAOProduct daoProduct;

    public ShoppingCart addShoppingCartFirstTime(Long id, String sessionToken, int amount){
        ShoppingCart shoppingCart = new ShoppingCart();
        CartItem cartItem = new CartItem();
        cartItem.setDate(new Date());
        cartItem.setAmount(amount);
        cartItem.setProduct(daoProduct.getProductById(id));
        shoppingCart.getItems().add(cartItem);
        shoppingCart.setTokenSession(sessionToken);
        return shoppingCartRepository.save(shoppingCart);
    }

    public ShoppingCart addToExistingShoppingCart(Long id, String sessionToken, int amount){
        ShoppingCart shoppingCart = shoppingCartRepository.findByTokenSession(sessionToken);
        Product p = daoProduct.getProductById(id);
        for (CartItem item : shoppingCart.getItems()) {
            if (p.getId().equals(item.getProduct().getId())) {
                item.setAmount(item.getAmount() + amount);
                return shoppingCartRepository.saveAndFlush(shoppingCart);
            }
        }
        CartItem cardItem = new CartItem();
        cardItem.setDate(new Date());
        cardItem.setProduct(daoProduct.getProductById(id));
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

    public void buyShoppingCart(String sessionToken) {
        ShoppingCart sh = shoppingCartRepository.findByTokenSession(sessionToken);
        for(CartItem cartItem : sh.getItems()){
            System.out.println(cartItem.getProduct().getName() + " count = " + cartItem.getAmount() +
                    " date = " + cartItem.getDate());
        }
        shoppingCartRepository.delete(sh);
    }
}
