package com.packet.systems.model;

import io.vertx.core.json.Json;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCart {

    private double cartItemTotal;
    private double cartItemPromoSavings;
    private double shippingTotal;
    private double shippingPromoSavings;
    private double cartTotal;
    private String cartId;
    private List<ShoppingCartItem> shoppingCartItemList = new ArrayList<ShoppingCartItem>();

    public ShoppingCart(String cartId) {
        this.cartId = cartId;
    }

    public void addShoppingCartItem(ShoppingCartItem sci) {
        if (sci != null) {
            shoppingCartItemList.add(sci);
        }
    }

    public boolean removeShoppingCartItem(ShoppingCartItem sci) {
        boolean removed = false;

        if (sci != null) {
            removed = shoppingCartItemList.remove(sci);
        }

        return removed;
    }

    public void resetShoppingCartItemList() {
        shoppingCartItemList = new ArrayList<ShoppingCartItem>();
    }

    @Override
    public String toString() {
        return Json.encode(this);
    }
}
