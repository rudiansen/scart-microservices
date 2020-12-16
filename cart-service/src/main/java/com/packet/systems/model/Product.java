package com.packet.systems.model;

import io.vertx.core.json.Json;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private String itemId;
    private String name;
    private String desc;
    private double price;

    @Override
    public String toString() {
        return Json.encode(this);
    }
}
