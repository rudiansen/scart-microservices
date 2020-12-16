package com.packet.systems.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class OrderItem {

    private int quantity;
    private String productId;

}
