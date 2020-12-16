package com.packet.systems.model.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private String orderId;
    private String total;
    private CreditCard creditCard;
    private String billingAddress;
    private String name;

}
