package com.backend.app.models.responses.openAI;

import com.backend.app.models.dtos.openAI.MessageDto;
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
            MessageDto message,
            @JsonProperty("finish_reason") String finishReason
    ) { }
}

