package com.packet.systems.service;

import com.packet.systems.model.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    ProductRepository repository;

    @Test
    public void test_readOne() {
        Product product = repository.findById("444434");
        assertThat(product).isNotNull();
        assertThat(product.getName()).as("Verify product name").isEqualTo("Red Hat Impact T-shirt");
        assertThat(product.getQuantity()).as("Quantity should be ZERO").isEqualTo(0);
    }

    @Test
    public void test_readAll() {
        List<Product> productList = repository.readAll();
        assertThat(productList).isNotNull();
        assertThat(productList).isNotEmpty();
        List<String> names = productList.stream().map(Product::getName).collect(Collectors.toList());
        assertThat(names).contains("Quarkus T-shirt", "Knit socks", "Red Hat Impact T-shirt");
    }
}
