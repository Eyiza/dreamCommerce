package org.dreamcommerce.dreamcommerce.service;

import lombok.RequiredArgsConstructor;
import org.dreamcommerce.dreamcommerce.dto.request.AddProductRequest;
import org.dreamcommerce.dreamcommerce.dto.response.AddProductResponse;
import org.dreamcommerce.dreamcommerce.model.Product;
import org.dreamcommerce.dreamcommerce.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public AddProductResponse addProduct(AddProductRequest productRequest) {
        ModelMapper modelMapper = new ModelMapper();
        Product product = modelMapper.map(productRequest, Product.class);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, AddProductResponse.class);
    }
}
