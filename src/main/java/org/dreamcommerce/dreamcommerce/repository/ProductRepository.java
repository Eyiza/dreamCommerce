package org.dreamcommerce.dreamcommerce.repository;

import org.dreamcommerce.dreamcommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}
