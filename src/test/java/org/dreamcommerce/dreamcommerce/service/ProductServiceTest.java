package org.dreamcommerce.dreamcommerce.service;

import org.dreamcommerce.dreamcommerce.dto.request.AddProductRequest;
import org.dreamcommerce.dreamcommerce.dto.response.AddProductResponse;
import org.dreamcommerce.dreamcommerce.model.Product;
import org.dreamcommerce.dreamcommerce.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Test
    void testCanAddProduct(){
        AddProductRequest productRequest = new AddProductRequest();

        Product savedProduct = new Product();
        savedProduct.setId("xyz");

        ProductRepository productRepository = Mockito.mock(ProductRepository.class);
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(savedProduct);

        ProductServiceImpl productService = new ProductServiceImpl(productRepository);
        AddProductResponse productResponse = productService.addProduct(productRequest);

        assertNotNull(productResponse);
        assertThat(productResponse.getId()).isNotNull();
    }
}
