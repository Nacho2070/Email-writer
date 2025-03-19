package com.email.writer.app;

import com.email.writer.app.mapper.mapper;
import com.email.writer.app.request.EmailRequest;
import com.email.writer.app.request.Tone;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

import org.apache.logging.log4j.Logger;

@Slf4j
@Service
public class EmailGeneratorService {

    private static final Logger logger = LogManager.getLogger(EmailGeneratorService.class);

    private static final WebClient webClient = WebClient.create();

    @Value("${gemini.api.key}")
    private String key;

    @Value("${gemini.api.url}")
    private String URL;

    public String generateEmailReply(EmailRequest emailRequest) {

        String prompt = buildPrompt(emailRequest);
        logger.info(prompt);
        Map<String, Object> body = Map.of(
                "contents", new Object[]{
                        Map.of("parts",new Object[]{
                                Map.of("text", prompt)})
                }
        );

        JsonNode response = doPost(body);
        return mapper.extractResponseContent(response);
    }

    private String buildPrompt(EmailRequest emailRequest){
        StringBuilder prompt = new StringBuilder();

        prompt.append("Generate a professional email reply for the following email, must be in Spanish");
        if(emailRequest.tone() != null && !emailRequest.mailContent().isEmpty()){
            prompt.append("Use a ").append( emailRequest.tone() ).append(" tone");
        }
        prompt.append("\nOriginal email: ").append(emailRequest.mailContent());
        return prompt.toString();

    }
    private JsonNode doPost(Map<String, Object> body) {
    try {
        return webClient.post().uri(URL + key)
                .header("Content-Type", "application/json")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
    }catch (Exception e){
        logger.error(e.getMessage());
    }
        return null;
    }

}
