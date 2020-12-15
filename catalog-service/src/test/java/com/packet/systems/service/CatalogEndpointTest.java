package com.packet.systems.service;

import com.packet.systems.model.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CatalogEndpointTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void test_retrieving_one_product() {
        ResponseEntity<Product> response = restTemplate.getForEntity("/api/product/329199", Product.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .returns("329199", Product::getItemId)
                .returns("Pronounced Kubernetes", Product::getName)
                .returns(512, Product::getQuantity)
                .returns(9.0, Product::getPrice);
    }

    @Test
    public void check_that_endpoint_returns_a_correct_list() {

        ResponseEntity<List<Product>> response = restTemplate.exchange("/api/products",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Product>>() {
                });

        List<Product> productList = response.getBody();
        assertThat(productList).isNotNull();
        assertThat(productList).isNotEmpty();
        List<String> names = productList.stream().map(Product::getName).collect(Collectors.toList());
        assertThat(names).contains("Quarkus T-shirt", "Knit socks", "Red Hat Impact T-shirt");

        Product quarkus = productList.stream().filter(p -> p.getItemId().equals("329299")).findAny().get();
        assertThat(quarkus)
                .returns("329299", Product::getItemId)
                .returns("Quarkus T-shirt", Product::getName)
                .returns(736, Product::getQuantity)
                .returns(10.0, Product::getPrice);
    }

}
