package com.backend.app.models.dtos.openAI;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatDto {
    @NotNull(message = "Messages is required")
    private List<MessageDto> messages;
}
