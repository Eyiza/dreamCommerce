package org.dreamcommerce.dreamcommerce.controller;

import com.sun.security.auth.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.dreamcommerce.dreamcommerce.dto.request.AddProductRequest;
import org.dreamcommerce.dreamcommerce.dto.request.UpdateProductRequest;
import org.dreamcommerce.dreamcommerce.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody AddProductRequest addProductRequest) {
    // public ResponseEntity<?> addProduct(@RequestBody AddProductRequest addProductRequest, @AuthenticationPrincipal UserPrincipal userPrincipal) { // If you want to use data from the security context
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.addProduct(addProductRequest));
    }

    @PostMapping(value = "/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateProduct(@PathVariable String productId,
                                           @ModelAttribute UpdateProductRequest updateProductRequest) {
        return ResponseEntity.ok(productService.updateProduct(productId, updateProductRequest));
    }
}
