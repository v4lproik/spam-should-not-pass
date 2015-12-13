package net.v4lproik.spamshouldnotpass.spring.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ListUUIDDeserializer extends JsonDeserializer<List> {
    @Override
    public List<UUID> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        ArrayList<UUID> result = new ArrayList<>(node.size());

        for (int i = 0; i < node.size(); i++) {
            result.add(UUID.fromString(node.get(i).asText()));
        }

        return result;
    }
}