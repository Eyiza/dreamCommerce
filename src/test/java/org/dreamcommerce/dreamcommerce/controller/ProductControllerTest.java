package org.dreamcommerce.dreamcommerce.controller;

import lombok.extern.slf4j.Slf4j;
import org.dreamcommerce.dreamcommerce.dto.request.AddProductRequest;
import org.dreamcommerce.dreamcommerce.dto.request.UpdateProductRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@AutoConfigureMockMvc // Tells Spring to place the mockMvc in the test context.
@ActiveProfiles(profiles = "test")
@Slf4j // From lombok for logging
@WithMockUser(roles = {"TEST"})
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc; //

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName(
            """
            Given:
            
            When:
            
            Check:
            
            """
    )
    void testAddProduct() {
        AddProductRequest productRequest = new AddProductRequest();

        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/product")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsBytes(productRequest))) // Convert to JSON
                    .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                    .andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            assertNull(e);
        }
    }

    @Test
    @Sql(scripts = {"/db/data.sql"})
    void testUpdateProduct() {
        try {
            String productId = "a9386116-fde5-4e27-baef-e7db7f6a5bdb";
            String name = "Candy";

            mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/product/" + productId)
                    .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                    .part( new MockPart("name", name.getBytes())) // Form field name and a value bytes array
            )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Candy"))
            .andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            log.error("ERROR: {}", e.getMessage());
            assertNull(e);
        }
    }

    @Test
    @Sql(scripts = {"/db/data.sql"})
    void testUpdateImage() {
        try {
            String productId = "a9386116-fde5-4e27-baef-e7db7f6a5bdb";
            final String imageLocation = "C:\\Users\\USER\\Downloads\\DreamDevs\\dreamCommerce\\src\\main\\resources\\static\\test.png";
            Path path = Path.of(imageLocation);
            InputStream imageStream = Files.newInputStream(path);

            mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/product/" + productId)
                            .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                            .part( new MockPart("name", "Candy".getBytes())) // Form field name and a value bytes array
                            .file("images", new MockMultipartFile("image", imageStream).getBytes())
                    )
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.images").exists())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.images").isNotEmpty())
                    .andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            log.error("ERROR: {}", e.getMessage());
            assertNull(e);
        }
    }
}
