package org.dreamcommerce.dreamcommerce.service;

import lombok.RequiredArgsConstructor;
import org.dreamcommerce.dreamcommerce.dto.request.AddProductRequest;
import org.dreamcommerce.dreamcommerce.dto.request.UpdateProductRequest;
import org.dreamcommerce.dreamcommerce.dto.response.AddProductResponse;
import org.dreamcommerce.dreamcommerce.dto.response.UpdateProductResponse;
import org.dreamcommerce.dreamcommerce.exception.ResourceNotFoundException;
import org.dreamcommerce.dreamcommerce.model.Product;
import org.dreamcommerce.dreamcommerce.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public AddProductResponse addProduct(AddProductRequest productRequest) {
        Product product = modelMapper.map(productRequest, Product.class);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, AddProductResponse.class);
    }

    @Override
    public UpdateProductResponse updateProduct(String id, UpdateProductRequest updateProductRequest) {
        // Get the product
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                String.format("Product with id: %s not found", id)
        ));

        // Upload images if present
        boolean isUpdateRequestWithUploads = updateProductRequest.getImages() != null && !updateProductRequest.getImages().isEmpty();
        if (isUpdateRequestWithUploads) {
            //TODO: upload images
        }

        // Update the product and upload images if present
        product = modelMapper.map(updateProductRequest, Product.class); // This overload of map() updates the product with the fields in updateProductRequest

        //save
        return modelMapper.map(productRepository.save(product), UpdateProductResponse.class);
    }
}
