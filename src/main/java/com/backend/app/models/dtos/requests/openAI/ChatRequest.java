package com.backend.app.models.dtos.requests.openAI;

import com.backend.app.models.dtos.annotations.NotNullAndNotEmpty;
import com.backend.app.persistence.enums.ERoleOpenAI;
import com.backend.app.utilities.RequestValidatorUtility;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ChatRequest {
    @NotNull(message = "Messages is required")
    private List<MessageRequest> messages;

    public ChatRequest(List<MessageRequest> messages) {
        this.messages = messages;

        RequestValidatorUtility.validate(this);
    }

    @Setter
    @Getter
    public static class MessageRequest {
        @NotNull(message = "Message cannot be null or empty")
        private ERoleOpenAI role;

        @NotNullAndNotEmpty(message = "Message cannot be null or empty")
        private String content;

        public MessageRequest(ERoleOpenAI role, String content) {
            this.role = role;
            this.content = content;

            RequestValidatorUtility.validate(this);
        }
    }
}
