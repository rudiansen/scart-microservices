package com.packet.systems.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory implements Serializable {

    private static final long serialVersionUID = 690417095744729438L;

    private int id;
    private String itemId;
    private String location;
    private int quantity;
    private String link;

    @Override
    public String toString() {
        return "Inventory{" +
                "id=" + id +
                ", itemId='" + itemId + '\'' +
                ", location='" + location + '\'' +
                ", quantity=" + quantity +
                ", link='" + link + '\'' +
                '}';
    }
}
