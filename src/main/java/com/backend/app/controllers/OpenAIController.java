package com.backend.app.controllers;

import com.backend.app.exceptions.CustomException;
import com.backend.app.exceptions.DtoException;
import com.backend.app.models.dtos.openAI.ChatDto;
import com.backend.app.models.responses.openAI.ChatResponse;
import com.backend.app.services.OpenAIServiceImpl;
import com.backend.app.utilities.DtoValidatorUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/openai")
public class OpenAIController {

    @Autowired
    private OpenAIServiceImpl openAIServiceImpl;

    @GetMapping("/greet")
    public ResponseEntity<ChatResponse> greetUser() {
        return new ResponseEntity<>(openAIServiceImpl.greetUser(), HttpStatus.OK);
    }

    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatDto chatDto) {
        DtoException<ChatDto> chatDtoException = DtoValidatorUtility.validate(chatDto);
        if (chatDtoException.getError() != null) throw CustomException.badRequest(chatDtoException.getError());
        return new ResponseEntity<>(openAIServiceImpl.chat(chatDtoException.getData()), HttpStatus.OK);
    }


}
