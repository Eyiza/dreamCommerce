package org.dreamcommerce.dreamcommerce.service.cloud;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.AllArgsConstructor;
import org.dreamcommerce.dreamcommerce.exception.FileUploadFailedException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@AllArgsConstructor
@Service
public class CloudServiceImpl implements CloudService{
    private final Cloudinary cloudinary;

    @Override
    public String uploadImage(byte[] image) {
        try {
            return cloudinary.uploader().upload(image, ObjectUtils.emptyMap()).get("url").toString();
        } catch (IOException e){
            throw new FileUploadFailedException("Failed to upload image");
        }
    }
}
