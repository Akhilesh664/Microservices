package com.microservice.ProductCatalog.Repository;

import com.microservice.ProductCatalog.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}