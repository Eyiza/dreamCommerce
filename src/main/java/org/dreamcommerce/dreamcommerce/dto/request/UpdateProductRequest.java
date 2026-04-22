package org.dreamcommerce.dreamcommerce.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class UpdateProductRequest {
    private String name;
    private String description;
    private String price;
    private String category;
    private List<MultipartFile> images;

}
