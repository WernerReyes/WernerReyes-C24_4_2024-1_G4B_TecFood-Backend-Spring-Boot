package com.backend.app.models.dtos.openAI;

import com.backend.app.models.dtos.NotNullAndNotEmpty;
import com.backend.app.persistence.enums.ERoleOpenAI;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    @NotNull(message = "Message cannot be null or empty")
    private ERoleOpenAI role;

    @NotNullAndNotEmpty(message = "Message cannot be null or empty")
    private String content;
}
