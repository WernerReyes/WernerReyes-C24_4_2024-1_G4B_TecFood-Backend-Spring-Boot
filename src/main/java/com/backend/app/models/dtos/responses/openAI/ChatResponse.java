package com.backend.app.models.dtos.responses.openAI;

import com.backend.app.models.dtos.requests.openAI.ChatRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"id","created","choices"})
public record ChatResponse(
        String id,
        int created,
        List<Choice> choices
) {

    public record Choice(
            ChatRequest.MessageRequest message
            // @JsonProperty("finish_reason") String finishReason
    ) { }
}

