package com.backend.app.models;

import com.backend.app.models.dtos.openAI.ChatDto;
import com.backend.app.models.responses.openAI.ChatResponse;

public interface IOpenAIService {
    ChatResponse chat(ChatDto chatDto);
    ChatResponse greetUser();
}
