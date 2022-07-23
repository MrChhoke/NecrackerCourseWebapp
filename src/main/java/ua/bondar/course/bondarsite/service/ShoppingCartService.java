package ua.bondar.course.bondarsite.service;

import ua.bondar.course.bondarsite.model.item.CartItem;
import ua.bondar.course.bondarsite.model.item.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    ShoppingCart addShoppingCartFirstTime(Long id, String sessionToken, int amount);
    ShoppingCart addToExistingShoppingCart(Long id, String sessionToken, int amount);
    ShoppingCart getShoppingCartByTokenSession(String sessionToken);
    CartItem updateShoppingCartItem(Long id, int amount);
    ShoppingCart removeCartItemFromShoppingCard(Long id, String sessionToken);
    void clearShoppingCart(String sessionToken);
    ShoppingCart buyShoppingCart(String sessionToken, String location, String buyerName);
    void acceptOrder(Long id);
    List<ShoppingCart> getShoppingCartsByActive(Boolean active);
    List<ShoppingCart> getHistoryOfUser(String username);
    List<ShoppingCart> getFullHistoryOrderHistory();
}
