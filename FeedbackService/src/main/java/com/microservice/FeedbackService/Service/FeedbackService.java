package com.microservice.FeedbackService.Service;

import com.microservice.FeedbackService.Dto.FeedbackRequestDTO;
import com.microservice.FeedbackService.Dto.FeedbackResponseDTO;
import java.util.List;

public interface FeedbackService {
    FeedbackResponseDTO createFeedback(FeedbackRequestDTO requestDTO);
    List<FeedbackResponseDTO> getFeedbackByProductId(Long productId);
}
