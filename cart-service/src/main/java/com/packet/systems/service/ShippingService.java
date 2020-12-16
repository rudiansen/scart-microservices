package com.packet.systems.service;

import com.packet.systems.model.ShoppingCart;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ShippingService {

    public void calculateShipping(ShoppingCart shoppingCart) {
        if (shoppingCart != null) {
            if (shoppingCart.getCartItemTotal() >= 0 && shoppingCart.getCartItemTotal() < 25) {
                shoppingCart.setShippingTotal(2.99);
            } else if (shoppingCart.getCartItemTotal() >= 25 && shoppingCart.getCartItemTotal() < 50) {
                shoppingCart.setShippingTotal(4.99);
            } else if (shoppingCart.getCartItemTotal() >= 50 && shoppingCart.getCartItemTotal() < 75) {
                shoppingCart.setShippingTotal(6.99);
            } else if (shoppingCart.getCartItemTotal() >= 75 && shoppingCart.getCartItemTotal() < 100) {
                shoppingCart.setShippingTotal(8.99);
            } else if (shoppingCart.getCartItemTotal() >= 100 && shoppingCart.getCartItemTotal() < 10000) {
                shoppingCart.setShippingTotal(10.99);
            }
        }
    }

}
