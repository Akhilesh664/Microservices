package com.microservice.ProductCatalog.Service;

import com.microservice.ProductCatalog.Model.Product;
import com.microservice.ProductCatalog.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Create product method
    public Product createProduct(Product product){
        if (product.getPrice() < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        return productRepository.save(product);
    }

    // Getting all product
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id){
        return productRepository.findById(id);
    }

    // updating product in repository
    public Product updateProduct(Long id, Product product){
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isEmpty()){
            throw new IllegalArgumentException("Product not found");
        }
        if (product.getPrice() < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        Product updatedProduct = existingProduct.get();
        updatedProduct.setName(product.getName());
        updatedProduct.setPrice(product.getPrice());
        updatedProduct.setDescription(product.getDescription());
        return productRepository.save(updatedProduct);
    }

    // delete products from repository
    public void deleteProduct(Long id){
        if (!productRepository.existsById(id)){
            throw new IllegalArgumentException("Product not found");
        }
        productRepository.deleteById(id);
    }

}
