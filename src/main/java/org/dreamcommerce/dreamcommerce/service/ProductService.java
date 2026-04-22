package org.dreamcommerce.dreamcommerce.service;

import org.dreamcommerce.dreamcommerce.dto.request.AddProductRequest;
import org.dreamcommerce.dreamcommerce.dto.request.UpdateProductRequest;
import org.dreamcommerce.dreamcommerce.dto.response.AddProductResponse;
import org.dreamcommerce.dreamcommerce.dto.response.UpdateProductResponse;

public interface ProductService {
    AddProductResponse addProduct(AddProductRequest productRequest);

    UpdateProductResponse updateProduct(String id, UpdateProductRequest updateProductRequest);
}
