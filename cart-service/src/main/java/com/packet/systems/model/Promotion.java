package com.packet.systems.model;

import io.vertx.core.json.Json;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Promotion {

    private String itemId;
    private double percentOff;

    @Override
    public String toString() {
        return Json.encode(this);
    }
}
