package org.dreamcommerce.dreamcommerce.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.UUID;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = UUID)
    private String id;
    private String name;
    private List<String> images = new ArrayList<>();
}
