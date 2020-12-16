package com.packet.systems.model.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditCard {

    private String name;
    private String number;
    private String nameOnCard;

}
