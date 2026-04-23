package org.dreamcommerce.dreamcommerce.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudConfig {
    @Value("${api.cloudinary.name}")
    private String cloudName;

    @Value("${api.cloudinary.key}")
    private String apiKey;

    @Value("${api.cloudinary.secret}")
    private String apiSecret;

    @Bean
    // First create your Cloudinary instance - https://cloudinary.com/documentation/java_integration
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "my_cloud_name",
                "api_key", "my_api_key",
                "api_secret", "my_api_secret",
                "secure", true));
    }

}
