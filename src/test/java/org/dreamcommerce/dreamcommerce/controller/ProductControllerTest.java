package org.dreamcommerce.dreamcommerce.controller;

import org.dreamcommerce.dreamcommerce.dto.request.AddProductRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tools.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@AutoConfigureMockMvc // Tells Spring to place the mockMvc in the test context.
@ActiveProfiles(profiles = "test")
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
    @WithMockUser(roles = {"TEST"})
    void testAddProduct() {
        AddProductRequest productRequest = new AddProductRequest();

        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsBytes(productRequest)))
                    .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                    .andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            assertNull(e);
        }


    }
}
