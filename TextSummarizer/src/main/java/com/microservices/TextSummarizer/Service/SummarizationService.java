package com.microservices.TextSummarizer.Service;

import com.microservices.TextSummarizer.Model.OnnxModel;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.io.File;

@Service
public class SummarizationService {
    private static final Logger logger = LoggerFactory.getLogger(SummarizationService.class);
    private OnnxModel model;

    @PostConstruct
    public void init() throws Exception {
        try {
            ClassPathResource encoderResource = new ClassPathResource("model/encoder_model.onnx");
            ClassPathResource decoderResource = new ClassPathResource("model/decoder_model.onnx");
            File encoderFile = encoderResource.getFile();
            File decoderFile = decoderResource.getFile();
            String encoderModelPath = encoderFile.getAbsolutePath();
            String decoderModelPath = decoderFile.getAbsolutePath();
            logger.info("Loading encoder model from: {}", encoderModelPath);
            logger.info("Loading decoder model from: {}", decoderModelPath);
            this.model = new OnnxModel(encoderModelPath, decoderModelPath);
        } catch (Exception e) {
            logger.error("Failed to initialize ONNX model: {}", e.getMessage(), e);
            throw e;
        }
    }

    public String summarize(String text) throws Exception {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Input text cannot be empty");
        }
        return model.summarize(text);
    }

    @PreDestroy
    public void destroy() throws Exception {
        if (model != null) {
            model.close();
        }
    }
}
