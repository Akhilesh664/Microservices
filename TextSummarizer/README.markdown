# Text Summarizer Microservice

This is a Spring Boot-based microservice for text summarization using ONNX models. It provides a REST API and a Thymeleaf-based web interface to summarize input text using an encoder-decoder ONNX model architecture. The application leverages the ONNX Runtime for model inference and includes a placeholder preprocessing and postprocessing pipeline for tokenization and detokenization.

## Table of Contents
- [Project Overview](#project-overview)
- [Project Structure](#project-structure)
- [Application Flow](#application-flow)
- [Prerequisites](#prerequisites)
- [Setup Instructions](#setup-instructions)
- [Dependencies](#dependencies)
- [Running the Application](#running-the-application)
- [Testing the Application](#testing-the-application)
- [Troubleshooting Common Issues](#troubleshooting-common-issues)
- [Necessary Knowledge](#necessary-knowledge)
- [Future Improvements](#future-improvements)

## Project Overview
The Text Summarizer microservice allows users to submit text via a REST API or a web interface and receive a summarized version. It uses two ONNX models (`encoder_model.onnx` and `decoder_model.onnx`) for text summarization. The application is built with Spring Boot, uses Thymeleaf for the web UI, and integrates the ONNX Runtime for model inference. The current implementation includes placeholder preprocessing and postprocessing logic, which should be replaced with a proper tokenizer (e.g., Hugging Face’s tokenizer) for production use.

## Project Structure
The project follows a standard Maven/Gradle structure with the following key components:

```
TextSummarizer/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/microservices/TextSummarizer/
│   │   │       ├── Controller/
│   │   │       │   └── SummarizerController.java
│   │   │       ├── Service/
│   │   │       │   └── SummarizationService.java
│   │   │       ├── Model/
│   │   │       │   └── OnnxModel.java
│   │   │       ├── Dto/
│   │   │       │   ├── TextRequest.java
│   │   │       │   └── TextResponse.java
│   │   │       └── TextSummarizerApplication.java
│   │   ├── resources/
│   │   │   ├── model/
│   │   │   │   ├── encoder_model.onnx
│   │   │   │   └── decoder_model.onnx
│   │   │   ├── templates/
│   │   │   │   └── index.html
│   │   │   └── application.properties
│   └── test/
│       └── java/
│           └── com/microservices/TextSummarizer/
├── pom.xml (or build.gradle)
└── README.md
```

- **Controller/SummarizerController.java**: Handles HTTP requests, including a REST API (`/api/summarize`) and Thymeleaf endpoints (`/` and `/summarize`).
- **Service/SummarizationService.java**: Manages model initialization and summarization logic.
- **Model/OnnxModel.java**: Handles ONNX model inference using the ONNX Runtime.
- **Dto/**: Contains `TextRequest` and `TextResponse` for JSON serialization/deserialization.
- **TextSummarizerApplication.java**: Spring Boot application entry point.
- **resources/model/**: Stores `encoder_model.onnx` and `decoder_model.onnx`.
- **resources/templates/index.html**: Thymeleaf template for the web UI.
- **application.properties**: Configuration for Spring Boot (e.g., server port, logging).

## Application Flow
1. **Startup**:
   - The application initializes the `SummarizationService` bean, which loads the ONNX models (`encoder_model.onnx` and `decoder_model.onnx`) in the `@PostConstruct` method.
   - Models are loaded from `src/main/resources/model/` using the class loader.

2. **REST API Request**:
   - A POST request to `http://localhost:8080/api/summarize` with a JSON body (`{"text": "input text"}`) is handled by `SummarizerController.summarizeApi`.
   - The controller calls `SummarizationService.summarize`, which delegates to `OnnxModel.summarize`.
   - The response is a JSON object (`{"summary": "summarized text"}`).

3. **Thymeleaf UI**:
   - A GET request to `http://localhost:8080/` renders the `index.html` form.
   - A POST request to `/summarize` submits text, calls `SummarizationService.summarize`, and displays the summary in the UI.

4. **Model Inference**:
   - `OnnxModel.summarize` preprocesses the input text into `input_ids` and `attention_mask` tensors (both `int64`).
   - The encoder model processes these inputs to produce hidden states.
   - The decoder model uses the encoder’s output (`encoder_hidden_states`) to generate the summary.
   - The output is postprocessed into readable text.

## Prerequisites
- **Java**: JDK 23 (or compatible version).
- **Maven/Gradle**: For dependency management and building.
- **ONNX Models**: Valid `encoder_model.onnx` and `decoder_model.onnx` files compatible with ONNX Runtime 1.17.0.
- **Postman**: For testing the REST API.
- **Browser**: For testing the Thymeleaf UI (e.g., Chrome).
- **Optional**: Netron (https://netron.app/) to inspect ONNX model inputs/outputs.

## Setup Instructions
1. **Clone the Repository**:
   ```bash
   git clone <repository-url>
   cd TextSummarizer
   ```

2. **Place ONNX Models**:
   - Copy `encoder_model.onnx` and `decoder_model.onnx` to `src/main/resources/model/`.
   - Ensure the models are valid and compatible with ONNX Runtime 1.17.0.

3. **Configure Dependencies**:
   - Ensure `pom.xml` includes:
     ```xml
     <dependencies>
         <dependency>
             <groupId>org.springframework.boot</groupId>
             <artifactId>spring-boot-starter-web</artifactId>
             <version>3.4.5</version>
         </dependency>
         <dependency>
             <groupId>org.springframework.boot</groupId>
             <artifactId>spring-boot-starter-thymeleaf</artifactId>
             <version>3.4.5</version>
         </dependency>
         <dependency>
             <groupId>com.microsoft.onnxruntime</groupId>
             <artifactId>onnxruntime</artifactId>
             <version>1.17.0</version>
         </dependency>
         <dependency>
             <groupId>ai.djl.huggingface</groupId>
             <artifactId>tokenizers</artifactId>
             <version>0.32.0</version>
         </dependency>
     </dependencies>
     ```

4. **Configure `application.properties`**:
   ```properties
   server.port=8080
   logging.level.org.springframework=DEBUG
   logging.level.com.microservices.TextSummarizer=DEBUG
   ```

5. **Build the Project**:
   ```bash
   mvn clean install
   ```

## Dependencies
- **Spring Boot 3.4.5**: Web framework for REST API and Thymeleaf.
- **ONNX Runtime 1.17.0**: For loading and running ONNX models.
- **DJL HuggingFace Tokenizers 0.32.0**: For proper tokenization (optional, recommended for production).
- **Jackson**: For JSON serialization/deserialization.
- **Thymeleaf**: For rendering the web UI.

## Running the Application
1. **Run with Maven**:
   ```bash
   mvn spring-boot:run
   ```
2. **Run the JAR**:
   ```bash
   java -jar target/TextSummarizer.jar
   ```
3. **Verify Startup**:
   - Check logs for `Tomcat started on port 8080`.
   - Ensure no `OrtException` or `UnsatisfiedDependencyException` occurs.

## Testing the Application
### **REST API (Postman)**
1. **Configure Request**:
   - **Method**: POST
   - **URL**: `http://localhost:8080/api/summarize`
   - **Headers**:
     ```
     Content-Type: application/json
     ```
   - **Body** (raw, JSON):
     ```json
     {
         "text": "This is a sample text to summarize."
     }
     ```
2. **Expected Response** (200 OK):
   ```json
   {
       "summary": "Summary: X words summarized."
   }
   ```
   (With proper tokenization: `{"summary": "[Actual summary]"}`)

3. **Error Responses**:
   - **400 Bad Request**: Invalid JSON or empty text.
   - **415 Unsupported Media Type**: Missing `Content-Type: application/json`.
   - **500 Internal Server Error**: Model inference issues.

### **Thymeleaf UI (Browser)**
1. Open `http://localhost:8080/` in a browser.
2. Enter text in the form and submit.
3. Verify the summary appears in the UI.

### **Sample Thymeleaf Template (`index.html`)**
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Text Summarizer</title>
    <style>
        .error { color: red; }
    </style>
</head>
<body>
    <h1>Text Summarizer</h1>
    <form method="post" action="/summarize">
        <textarea name="text" rows="10" cols="50" th:text="${originalText}"></textarea><br/>
        <input type="submit" value="Summarize"/>
    </form>
    <div th:if="${summary}">
        <h2>Summary:</h2>
        <p th:class="${summary.startsWith('Error') ? 'error' : ''}" th:text="${summary}"></p>
    </div>
</body>
</html>
```

## Troubleshooting Common Issues
1. **Model Loading Error**:
   - **Error**: `OrtException: Load model from /path/to/encoder_model.onnx failed`
   - **Cause**: Missing or invalid ONNX model files.
   - **Fix**:
     - Ensure `encoder_model.onnx` and `decoder_model.onnx` are in `src/main/resources/model/`.
     - Verify file paths in `SummarizationService.init()`:
       ```java
       @PostConstruct
       public void init() throws Exception {
           URL encoderResource = getClass().getClassLoader().getResource("model/encoder_model.onnx");
           URL decoderResource = getClass().getClassLoader().getResource("model/decoder_model.onnx");
           if (encoderResource == null || decoderResource == null) {
               throw new IllegalStateException("Model files not found");
           }
           String encoderModelPath = new File(encoderResource.toURI()).getAbsolutePath();
           String decoderModelPath = new File(decoderResource.toURI()).getAbsolutePath();
           this.model = new OnnxModel(encoderModelPath, decoderModelPath);
       }
       ```
     - Use temporary files for JAR compatibility:
       ```java
       InputStream encoderStream = getClass().getClassLoader().getResourceAsStream("model/encoder_model.onnx");
       File encoderTempFile = File.createTempFile("encoder_model", ".onnx");
       Files.copy(encoderStream, encoderTempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
       ```

2. **Incorrect Input Data Type**:
   - **Error**: `Unexpected input data type. Actual: (tensor(float)), expected: (tensor(int64))`
   - **Cause**: `input_ids` tensor uses `float` instead of `int64`.
   - **Fix**:
     ```java
     private long[] preprocess(String inputText) {
         String[] words = inputText.toLowerCase().split("\\s+");
         long[] tokenIds = new long[words.length];
         for (int i = 0; i < words.length; i++) {
             tokenIds[i] = i + 1;
         }
         return tokenIds;
     }
     try (OnnxTensor inputTensor = OnnxTensor.createTensor(env, LongBuffer.wrap(inputArray), new long[]{1, inputArray.length})) {
         // ...
     }
     ```

3. **Missing Attention Mask**:
   - **Error**: `Missing Input: attention_mask`
   - **Cause**: Model requires `attention_mask` tensor.
   - **Fix**:
     ```java
     private Map<String, long[]> preprocess(String inputText) {
         String[] words = inputText.toLowerCase().split("\\s+");
         long[] tokenIds = new long[words.length];
         long[] attentionMask = new long[words.length];
         for (int i = 0; i < words.length; i++) {
             tokenIds[i] = i + 1;
             attentionMask[i] = 1;
         }
         Map<String, long[]> inputs = new HashMap<>();
         inputs.put("input_ids", tokenIds);
         inputs.put("attention_mask", attentionMask);
         return inputs;
     }
     try (OnnxTensor inputTensor = OnnxTensor.createTensor(env, LongBuffer.wrap(inputArray), new long[]{1, inputArray.length});
          OnnxTensor attentionMaskTensor = OnnxTensor.createTensor(env, LongBuffer.wrap(attentionMaskArray), new long[]{1, attentionMaskArray.length})) {
         Map<String, OnnxTensor> encoderInputs = new HashMap<>();
         encoderInputs.put("input_ids", inputTensor);
         encoderInputs.put("attention_mask", attentionMaskTensor);
         // ...
     }
     ```

4. **REST API Errors**:
   - **404 Not Found**: Ensure `@PostMapping("/api/summarize")` is on `summarizeApi`.
   - **400 Bad Request**: Verify JSON payload (`{"text": "input"}`).
   - **415 Unsupported Media Type**: Set `Content-Type: application/json` in Postman.

5. **Thymeleaf UI Errors**:
   - Ensure `index.html` is in `src/main/resources/templates/`.
   - Check server logs for exceptions during form submission.

## Necessary Knowledge
- **Java/Spring Boot**: Understanding of Spring Boot, dependency injection, REST controllers, and Thymeleaf.
- **ONNX Runtime**: Familiarity with ONNX models and the ONNX Runtime Java API.
- **NLP Models**: Knowledge of transformer-based models (e.g., BART, T5) and their input requirements (`input_ids`, `attention_mask`).
- **Tokenization**: Experience with NLP tokenizers (e.g., Hugging Face’s `tokenizers`) for preprocessing and postprocessing.
- **Maven/Gradle**: For managing dependencies and building the project.
- **Postman**: For testing REST APIs.
- **Netron**: For inspecting ONNX model inputs/outputs.

## Future Improvements
- **Proper Tokenization**: Replace placeholder `preprocess` and `postprocess` with Hugging Face’s tokenizer:
  ```java
  import ai.djl.huggingface.tokenizers.HuggingFaceTokenizer;

  private Map<String, long[]> preprocess(String inputText) throws Exception {
      HuggingFaceTokenizer tokenizer = HuggingFaceTokenizer.newInstance("facebook/bart-large-cnn");
      Encoding encoding = tokenizer.encode(inputText);
      Map<String, long[]> inputs = new HashMap<>();
      inputs.put("input_ids", encoding.getIds());
      inputs.put("attention_mask", encoding.getAttentionMask());
      return inputs;
  }

  private String postprocess(float[] outputArray) throws Exception {
      HuggingFaceTokenizer tokenizer = HuggingFaceTokenizer.newInstance("facebook/bart-large-cnn");
      long[] tokenIds = new long[outputArray.length];
      for (int i = 0; i < outputArray.length; i++) {
          tokenIds[i] = (long) outputArray[i];
      }
      return tokenizer.decode(tokenIds);
  }
  ```

- **Decoder Output Handling**: Implement proper decoding (e.g., beam search) if the decoder outputs logits.
- **Error Handling**: Add user-friendly error messages and logging.
- **Model Validation**: Use Netron to verify model inputs/outputs and test models in Python:
  ```python
  import onnxruntime as ort
  session = ort.InferenceSession("path/to/encoder_model.onnx")
  print("Inputs:", [input.name for input in session.get_inputs()])
  ```

- **Performance Optimization**: Cache the tokenizer instance and optimize tensor creation.
- **Testing**: Add unit tests for `SummarizationService` and integration tests for the REST API.