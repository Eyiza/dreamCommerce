package org.dreamcommerce.dreamcommerce.service;

import org.dreamcommerce.dreamcommerce.dto.request.AddProductRequest;
import org.dreamcommerce.dreamcommerce.dto.response.AddProductResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ProductServiceIntegrationTest {
    @Autowired
    private ProductService productService;

    @Test
    void testCanAddProduct() {
        AddProductRequest productRequest = new AddProductRequest();

        AddProductResponse productResponse = productService.addProduct(productRequest);

        assertNotNull(productResponse);
        assertThat(productResponse.getId()).isNotNull();
    }
}
