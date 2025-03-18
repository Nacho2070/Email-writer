package com.email.writer.app.mapper;

import com.fasterxml.jackson.databind.JsonNode;



public class mapper {

    public static String extractResponseContent(JsonNode node){
        try {
            return node
                    .path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text").asText();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        /*
        ArrayNode ss = (ArrayNode) node.get("candidates");
        StringBuilder text = new StringBuilder();
        ss.elements().forEachRemaining(candidate -> {
            JsonNode content = candidate.get("content");

            ArrayNode parts = (ArrayNode)content.get("parts");
            parts.elements().forEachRemaining(part -> {
                text.append( part.get("text").asText() );
                log.info("Response from gemini: ",text);
            });
        });
*/
        return null;
    }
}
