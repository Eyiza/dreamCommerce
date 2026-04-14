package org.dreamcommerce.dreamcommerce.service;

import org.dreamcommerce.dreamcommerce.dto.request.AddProductRequest;
import org.dreamcommerce.dreamcommerce.dto.response.AddProductResponse;

public interface ProductService {
    AddProductResponse addProduct(AddProductRequest productRequest);
}
