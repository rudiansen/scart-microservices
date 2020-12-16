package com.packet.systems.model;

import io.vertx.core.json.Json;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartItem {

    private double price;
    private int quantity;
    private double promoSavings;
    private Product product;

    @Override
    public String toString() {
        return Json.encode(this);
    }
}
