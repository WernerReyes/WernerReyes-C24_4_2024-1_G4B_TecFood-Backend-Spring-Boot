package com.backend.app.services;

import com.backend.app.models.IOpenAIService;
import com.backend.app.models.dtos.requests.openAI.ChatRequest;
import com.backend.app.models.dtos.responses.common.ApiResponse;
import com.backend.app.models.dtos.responses.openAI.ChatResponse;
import com.backend.app.persistence.entities.DishEntity;
import com.backend.app.persistence.entities.UserEntity;
import com.backend.app.persistence.enums.EResponseStatus;
import com.backend.app.persistence.enums.ERoleOpenAI;
import com.backend.app.persistence.repositories.DishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Qualifier("openaiRestTemplate")
@RequiredArgsConstructor
@Service
public class OpenAIServiceImpl implements IOpenAIService {

    private final DishRepository dishRepository;
    private final RestTemplate restTemplate;
    private final UserAuthenticationService userAuthenticationService;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    @Value("${frontend.url}")
    private String frontendUrl;

    @Override
    public ApiResponse<ChatResponse> greetUser() {
        HttpEntity<Map<String, Object>> entity = prepareRequest(
                new ChatRequest(new ArrayList<>(
                        List.of(new ChatRequest.MessageRequest(ERoleOpenAI.system, regart()))
                ))
        );
        try {
            return new ApiResponse<>(EResponseStatus.SUCCESS, "Success", restTemplate.postForObject(apiUrl, entity, ChatResponse.class));
        } catch (Exception e) {
            return errorResponse();
        }
    }

    @Override
    public ApiResponse<ChatResponse> chat(ChatRequest chatRequest) {
        HttpEntity<Map<String, Object>> entity = prepareRequest(chatRequest);
        try {
            return  new ApiResponse<>(EResponseStatus.SUCCESS, "Success", restTemplate.postForObject(apiUrl, entity, ChatResponse.class));
        } catch (Exception e) {
            return errorResponse();
        }
    }

    private HttpEntity<Map<String, Object>> prepareRequest(ChatRequest chatRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);

        //* Instructions to follow
        chatRequest.getMessages().add(new ChatRequest.MessageRequest(ERoleOpenAI.system, instructions()));

        requestBody.put("messages", chatRequest.getMessages());

        return new HttpEntity<>(requestBody, headers);
    }

    private ApiResponse<ChatResponse> errorResponse() {
        return new ApiResponse<>(EResponseStatus.ERROR,
                "Error",
                new ChatResponse("Error",  0,
                        List.of(new ChatResponse.Choice(new ChatRequest.MessageRequest(ERoleOpenAI.system, "Sorry, but I'm not avaiable at this momenent \uD83D\uDE1E")) )
                )
        );
    }

    private String regart() {
        UserEntity user = userAuthenticationService.find();
        return  formToFill() +  " Saluda con lo siguiente o con algo similar, pero siempre saludando y mencionando al usuario: Hola " + user.getFirstName() + " " + user.getLastName() + " Soy TecFood Bot \uD83E\uDD16, un asistente virtual que te ayudará a conocer más sobre los platillos que se ofrecen en el aplicativo. ¿En qué puedo ayudarte?";
    }

    private String instructions() {
        List<DishEntity> dishes = dishRepository.findAll();
        return  formToFill() + "Tu eres un asiste de una aplicacion llamada TecFood, donde brindaras información de " +
                "los platillos que se ofrecen en el aplicativo, para ello, puedes preguntar por el platillo que" +
                " desees conocer, por ejemplo: ¿Qué es el pozole? o ¿Cuál es el precio del pozole? Los platillos son los siguientes: "
                + dishes + ". Recuerda solo puedes responder preguntas sobre los platillos. ¿En qué puedo ayudarte? Además puedes brindar la información de los platillos con el siguiente enlace:" +
                frontendUrl + "  + /user/dishes/ y el id del platillo que se escogio. Por ejemplo: " + frontendUrl + "/user/dishes/1. " +
                "Despidete cordialmente con su nombre cuando el cliente ya no necesite más información. Si te preguntan sobre el creador de la aplicación, le dices Werner Reyes Pilco, y si preguntas mas del tema le das mi número personal: 902365775" +
                "y mi correo: werner.reyes@tecsup.edu.pe. Ojo siempre respodiendo todos las preguntas que el cliente pida, si la pregunta no es sobre los platillos o el creador, puedes responder con: " + "Lo siento, no puedo responder a esa pregunta. ¿En qué más puedo ayudarte?";
    }

    private String formToFill() {
        return "IMPORTANTE! Las respuestas deben ser SIEMPRE elementos html y puedes usar clases de tailwindcss para darle estilo a las respuestas si gustas, dentro de un className. Ejemplo: <div className=''>Hola</div>. Recuerda que las respuestas deben ser en formato HTML. Continua con lo siguiente: ";
    }


}
