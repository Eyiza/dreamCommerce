package org.dreamcommerce.dreamcommerce.service;

import org.dreamcommerce.dreamcommerce.dto.request.AddProductRequest;
import org.dreamcommerce.dreamcommerce.dto.request.UpdateProductRequest;
import org.dreamcommerce.dreamcommerce.dto.response.AddProductResponse;
import org.dreamcommerce.dreamcommerce.dto.response.UpdateProductResponse;
import org.dreamcommerce.dreamcommerce.model.Product;
import org.dreamcommerce.dreamcommerce.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks // Tell Spring to inject the mock repo and model mapper into product service
    private ProductServiceImpl productService;

    @Test
    void testCanAddProduct(){
        AddProductRequest productRequest = new AddProductRequest();
        AddProductResponse productResponse = new AddProductResponse();

        Product savedProduct = new Product();
        savedProduct.setId("xyz");

        productResponse.setId(savedProduct.getId());

        // For each time the dependency is used in the service, describe how the mocked version is used
        when(productRepository.save(Mockito.any(Product.class))).thenReturn(savedProduct);
        when(modelMapper.map(productRequest, Product.class)).thenReturn(new Product());
        when(modelMapper.map(savedProduct, AddProductResponse.class)).thenReturn(productResponse);

        AddProductResponse response = productService.addProduct(productRequest);

        assertNotNull(response);
        assertThat(response.getId()).isNotNull();
    }

    @Test
    void testCanUpdateProduct(){
        String productId = "xyz";
        UpdateProductRequest updateProductRequest = new UpdateProductRequest();
        updateProductRequest.setName("Chocolate");

        // Represents the product findById gives us
        Product product = new Product();
        product.setId(productId);
        product.setName(updateProductRequest.getName());

        UpdateProductResponse productResponse = new UpdateProductResponse();
        productResponse.setId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);
        when(modelMapper.map(product, UpdateProductResponse.class)).thenReturn(productResponse);
        // when(modelMapper.map(updateProductRequest, Product.class)).thenReturn(product);
        doNothing().when(modelMapper).map(updateProductRequest, product);

        UpdateProductResponse updateProductResponse = productService.updateProduct(productId, updateProductRequest);
        assertThat(updateProductResponse).isNotNull();
        assertThat(updateProductResponse.getId()).isEqualTo(productId);
    }
}
