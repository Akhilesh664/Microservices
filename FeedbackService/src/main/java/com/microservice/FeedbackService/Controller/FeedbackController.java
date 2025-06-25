package com.microservice.FeedbackService.Controller;

import com.microservice.FeedbackService.Dto.FeedbackRequestDTO;
import com.microservice.FeedbackService.Dto.FeedbackResponseDTO;
import com.microservice.FeedbackService.Service.FeedbackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping
    public ResponseEntity<FeedbackResponseDTO> createFeedback(@Valid @RequestBody FeedbackRequestDTO requestDTO){
        FeedbackResponseDTO responseDTO = feedbackService.createFeedback(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<FeedbackResponseDTO>> getFeedbackByProductId(@PathVariable Long productId) {
        List<FeedbackResponseDTO> feedbackList = feedbackService.getFeedbackByProductId(productId);
        return ResponseEntity.ok(feedbackList);
    }

}
