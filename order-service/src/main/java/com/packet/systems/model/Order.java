package com.packet.systems.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Order {

    private String orderId;
    private String name;
    private String total;
    private String ccNumber;
    private String ccExp;
    private String billingAddress;
    private String status;

}
