package com.example.SentimentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/sentiment")
public class SentimentController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("{meaningcloud.api.key}}")
    private String apikey;

    @PostMapping("/analyze/{reviewId}")
    public ResponseEntity<String> analyzeSentiment(@PathVariable Long reviewId){
        // Fetch review from Review Service
        ResponseEntity<Review> response = restTemplate.getForEntity("http://localhost:8080/api/reviews/"
                + reviewId, Review.class);

        Review review = response.getBody();

        if (review == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Review not found");
        }

        // Call MeaningCloud API
        String url = "https://api.meaningcloud.com/sentiment-2.1?key="
                + apikey + "&txt=" + review.getReviewText() + "&lang=en";
        ResponseEntity<Map> apiResponse = restTemplate.getForEntity(url, Map.class);
        Map<String, Object> result = apiResponse.getBody();

        // Extract sentiment (simplified)
        String sentiment = result != null && result.containsKey("score_tag") ?
        (String) result.get("score_tag") : "UNKNOWN";
        review.setSentiment(sentiment);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Review> request = new HttpEntity<>(review, headers);
        restTemplate.put("http://localhost:8080/api/reviews/" + reviewId, request);

        return ResponseEntity.ok("Sentiment analyzed: " + sentiment);

    }

}

class Review {
    private Long id;
    private String productId;
    private String reviewText;
    private String sentiment;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    public String getReviewText() { return reviewText; }
    public void setReviewText(String reviewText) { this.reviewText = reviewText; }
    public String getSentiment() { return sentiment; }
    public void setSentiment(String sentiment) { this.sentiment = sentiment; }
}