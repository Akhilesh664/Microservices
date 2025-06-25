package com.microservice.FeedbackService.Dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class FeedbackRequestDTO {

    @NotNull(message = "Product ID is Required")
    private Long productId;

    @Min(value = 1, message = "Rating must be between 1 to 5")
    @Max(value = 5, message = "Rating must be between 1 to 5")
    @NotNull(message = "Rating is Required")
    private Integer rating;

    @Size(max = 500, message = "Comment cannot exceed 500 characters")
    private String comment;

    @NotBlank(message = "user Id is required")
    private String userId;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
