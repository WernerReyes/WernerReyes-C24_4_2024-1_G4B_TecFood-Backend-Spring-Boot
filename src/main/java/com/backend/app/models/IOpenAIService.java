package com.backend.app.models;

import com.backend.app.models.dtos.requests.openAI.ChatRequest;
import com.backend.app.models.dtos.responses.common.ApiResponse;
import com.backend.app.models.dtos.responses.openAI.ChatResponse;

public interface IOpenAIService {
    ApiResponse<ChatResponse> chat(ChatRequest chatRequest);
    ApiResponse<ChatResponse> greetUser();
}
