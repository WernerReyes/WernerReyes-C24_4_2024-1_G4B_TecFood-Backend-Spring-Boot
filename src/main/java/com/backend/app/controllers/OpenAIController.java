package com.backend.app.controllers;

import com.backend.app.models.IOpenAIService;
import com.backend.app.models.dtos.requests.openAI.ChatRequest;
import com.backend.app.models.dtos.responses.common.ApiResponse;
import com.backend.app.models.dtos.responses.openAI.ChatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/openai")
public class OpenAIController {

    private final IOpenAIService openAIService;

    @GetMapping("/greet")
    public ResponseEntity<ApiResponse<ChatResponse>> greetUser() {
        return new ResponseEntity<>(openAIService.greetUser(), HttpStatus.OK);
    }

    @PostMapping("/chat")
    public ResponseEntity<ApiResponse<ChatResponse>> chat(@RequestBody ChatRequest chatRequest) {
        return new ResponseEntity<>(openAIService.chat(chatRequest), HttpStatus.OK);
    }


}
