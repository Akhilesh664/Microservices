package com.example.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping
    public ResponseEntity<Review> submitReview(@RequestBody Review review){
        review.setSentiment("PENDING");
        Review savedReview = reviewRepository.save(review);
        // Call Sentiment Service to analyze sentiment
        restTemplate.postForEntity("http://localhost:8081/api/sentiment/analyze/" +
                savedReview.getId(), null, void.class);
        return ResponseEntity.ok(savedReview);
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        return ResponseEntity.ok(reviewRepository.findAll());
    }
}
