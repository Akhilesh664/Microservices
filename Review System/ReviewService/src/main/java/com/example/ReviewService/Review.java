package com.example.ReviewService;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Getter
@Setter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "review_text")
    private String reviewText;

    @Column(name = "sentiment")
    private String sentiment;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

//    // Getters and Setters
//    public Long getId() { return id; }
//    public void setId(Long id) { this.id = id; }
//    public String getProductId() { return productId; }
//    public void setProductId(String productId) { this.productId = productId; }
//    public String getReviewText() { return reviewText; }
//    public void setReviewText(String reviewText) { this.reviewText = reviewText; }
//    public String getSentiment() { return sentiment; }
//    public void setSentiment(String sentiment) { this.sentiment = sentiment; }
//    public LocalDateTime getCreatedAt() { return createdAt; }
//    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

}
