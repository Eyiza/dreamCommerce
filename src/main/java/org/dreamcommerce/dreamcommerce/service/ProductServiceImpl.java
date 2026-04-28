package org.dreamcommerce.dreamcommerce.service;

import lombok.RequiredArgsConstructor;
import org.dreamcommerce.dreamcommerce.dto.request.AddProductRequest;
import org.dreamcommerce.dreamcommerce.dto.request.UpdateProductRequest;
import org.dreamcommerce.dreamcommerce.dto.response.AddProductResponse;
import org.dreamcommerce.dreamcommerce.dto.response.UpdateProductResponse;
import org.dreamcommerce.dreamcommerce.exception.FileUploadFailedException;
import org.dreamcommerce.dreamcommerce.exception.ProductUpdateFailedException;
import org.dreamcommerce.dreamcommerce.exception.ResourceNotFoundException;
import org.dreamcommerce.dreamcommerce.model.Product;
import org.dreamcommerce.dreamcommerce.repository.ProductRepository;
import org.dreamcommerce.dreamcommerce.service.cloud.CloudService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CloudService cloudService;

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
            updateProductRequest.getImages().forEach(image -> uploadImage(image, product));
        }

        // Update the product and upload images if present
        modelMapper.map(updateProductRequest, product); // This overload of map() updates the product with the fields in updateProductRequest

        //save
        Product savedProduct = productRepository.save(product);

        return modelMapper.map(savedProduct, UpdateProductResponse.class);

    }

    private void uploadImage(MultipartFile image, Product product) {
        try {
            String imageUrl = cloudService.uploadImage(image.getBytes());
            product.getImages().add(imageUrl);
        } catch (IOException e) {
            throw new FileUploadFailedException("File upload failed");
        }
    }
}
