package com.packet.systems.service;

import com.packet.systems.client.InventoryClient;
import com.packet.systems.model.Product;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class CatalogService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    InventoryClient inventoryClient;

    public Product read(String id) {
        Product product = repository.findById(id);

        return setProductQuantity(product);
    }

    public List<Product> readAll() {
        List<Product> productList = repository.readAll();

        for (Product product : productList)
            setProductQuantity(product);

        return productList;
    }

    private Product setProductQuantity(Product product) {

        JSONArray jsonArray = new JSONArray(inventoryClient.getInventoryStatus(product.getItemId()));
        List<String> quantity = IntStream.range(0, jsonArray.length())
                .mapToObj(index -> ((JSONObject)jsonArray.get(index))
                        .optString("quantity")).collect(Collectors.toList());
        product.setQuantity(Integer.parseInt(quantity.get(0)));
        return product;
    }
}
