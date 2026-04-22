package org.dreamcommerce.dreamcommerce.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateProductResponse {
    private String id;
    private List<String> imageUrls;
}
