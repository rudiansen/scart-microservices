package com.packet.systems.service;

import com.packet.systems.model.Product;
import com.packet.systems.model.ShoppingCart;

public interface ShoppingCartService {

    public ShoppingCart getShoppingCart(String cartId);

    public Product getProduct(String itemId);

    public ShoppingCart deleteItem(String cartId, String itemId, int quantity);

    public ShoppingCart checkout(String cartId);

    public ShoppingCart addItem(String cartId, String itemId, int quantity);

    public ShoppingCart set(String cartId, String tmpId);

    public void priceShoppingCart(ShoppingCart sc);
}
