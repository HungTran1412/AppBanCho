package dev.mhung.ltmobile.petapplication.model;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static List<CartItem> cartList = new ArrayList<>();

    public static void addToCart(CartItem item) {
        for (CartItem existing : cartList) {
            if (existing.getName().equals(item.getName())) {
                int newQuantity = existing.getQuantity() + item.getQuantity();
                cartList.set(cartList.indexOf(existing), new CartItem(existing.getName(), existing.getPrice(), newQuantity, existing.getImageUrl()));
                return;
            }
        }
        cartList.add(item);
    }

    public static List<CartItem> getCart() {
        return cartList;
    }

    public static void clearCart() {
        cartList.clear();
    }
}
