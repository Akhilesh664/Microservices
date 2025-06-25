package com.microservices.TextSummarizer.Model;

import ai.djl.sentencepiece.SpProcessor;
import ai.onnxruntime.*;

import java.nio.FloatBuffer;
import java.nio.LongBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OnnxModel {
    private final OrtEnvironment env;
    private final OrtSession encoderSession;
    private final OrtSession decoderSession;
    private final SpProcessor tokenizer;

    public OnnxModel(String encoderModelPath, String decoderModelPath) throws OrtException {
        this.env = OrtEnvironment.getEnvironment();
        this.encoderSession = env.createSession(encoderModelPath, new OrtSession.SessionOptions());
        this.decoderSession = env.createSession(decoderModelPath, new OrtSession.SessionOptions());
        try {
            this.tokenizer = new SpProcessor("src/main/resources/model/spiece.model");
        } catch (Exception e) {
            throw new OrtException("Failed to load SentencePiece model: " + e.getMessage());
        }
    }

    public String summarize(String inputText) throws OrtException {
        long[] inputArray = preprocess(inputText);
        OnnxTensor inputTensor = OnnxTensor.createTensor(env, LongBuffer.wrap(inputArray), new long[]{1, inputArray.length});

        // Run encoder
        Map<String, OnnxTensor> encoderInputs = new HashMap<>();
        encoderInputs.put("input_ids", inputTensor);
        try (OrtSession.Result encoderResults = encoderSession.run(encoderInputs)) {
            float[] encoderOutput = (float[]) encoderResults.get(0).getValue();
            OnnxTensor decoderInput = OnnxTensor.createTensor(env, FloatBuffer.wrap(encoderOutput), new long[]{1, encoderOutput.length});

            // Run decoder
            Map<String, OnnxTensor> decoderInputs = new HashMap<>();
            decoderInputs.put("encoder_hidden_states", decoderInput);
            try (OrtSession.Result decoderResults = decoderSession.run(decoderInputs)) {
                float[] outputArray = (float[]) decoderResults.get(0).getValue();
                return postprocess(outputArray);
            }
        }
    }

    private long[] preprocess(String inputText) {
        // Tokenize using SentencePiece
        int[] tokenIds = tokenizer.encode(inputText);
        long[] result = new long[tokenIds.length];
        for (int i = 0; i < tokenIds.length; i++) {
            result[i] = tokenIds[i];
        }
        return result;
    }

    private String postprocess(float[] outputArray) {
        // Detokenize using SentencePiece
        long[] outputIds = new long[outputArray.length];
        for (int i = 0; i < outputArray.length; i++) {
            outputIds[i] = (long) outputArray[i];
        }
        return tokenizer.decode(List.of(outputIds));
    }

    public void close() throws OrtException {
        encoderSession.close();
        decoderSession.close();
        env.close();
        tokenizer.close();
    }
}