package com.packet.systems.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {

    private static final long serialVersionUID = -5692209559020180468L;

    private String itemId;
    private String name;
    private String desc;
    private double price;
    private int quantity;

    public Product(String itemId, String name, String desc, double price) {
        this.itemId = itemId;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "itemId='" + itemId + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
