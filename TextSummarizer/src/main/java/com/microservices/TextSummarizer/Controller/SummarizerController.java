package com.microservices.TextSummarizer.Controller;

import com.microservices.TextSummarizer.Dto.TextRequest;
import com.microservices.TextSummarizer.Dto.TextResponse;
import com.microservices.TextSummarizer.Service.SummarizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SummarizerController {

    @Autowired
    private SummarizationService summarizationService;

    // REST API endpoint
    @PostMapping("/api/summarize")
    public ResponseEntity<TextResponse> summarizeApi(@RequestBody TextRequest request){
        try {
            String summary = summarizationService.summarize(request.getText());
            return ResponseEntity.ok(new TextResponse(summary));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new TextResponse("Error: " + e.getMessage()));
        }
    }

    // Optional Thymeleaf UI endpoint
    @GetMapping("/")
    public String showForm() {
        return "index";
    }

    @PostMapping("/summarize")
    public String summarizeForm(@RequestParam("text") String text, Model model) {
        try {
            String summary = summarizationService.summarize(text);
            model.addAttribute("summary", summary);
        } catch (Exception e) {
            model.addAttribute("summary", "Error: " + e.getMessage());
        }
        model.addAttribute("originalText", text);
        return "index";
    }

}
