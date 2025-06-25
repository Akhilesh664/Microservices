package com.microservice.FeedbackService.Impl;

import com.microservice.FeedbackService.Dto.FeedbackRequestDTO;
import com.microservice.FeedbackService.Dto.FeedbackResponseDTO;
import com.microservice.FeedbackService.Entity.Feedback;
import com.microservice.FeedbackService.Repository.FeedbackRepository;
import com.microservice.FeedbackService.Service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;

    public FeedbackServiceImpl(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public FeedbackResponseDTO createFeedback(FeedbackRequestDTO requestDTO){
        Feedback feedback = new Feedback();
        feedback.setProductId(requestDTO.getProductId());
        feedback.setRating(requestDTO.getRating());
        feedback.setComment(requestDTO.getComment());
        feedback.setUserId(requestDTO.getUserId());

        Feedback savedFeedback = feedbackRepository.save(feedback);
        return mapToResponseDTO(savedFeedback);
    }

    @Override
    public List<FeedbackResponseDTO> getFeedbackByProductId(Long productId) {
        return feedbackRepository.findByProductId(productId)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    private FeedbackResponseDTO mapToResponseDTO(Feedback feedback) {
        FeedbackResponseDTO responseDTO = new FeedbackResponseDTO();
        responseDTO.setId(feedback.getId());
        responseDTO.setProductId(feedback.getProductId());
        responseDTO.setRating(feedback.getRating());
        responseDTO.setComment(feedback.getComment());
        responseDTO.setUserId(feedback.getUserId());
        return responseDTO;
    }


}
