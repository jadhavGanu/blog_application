package com.project.blogging.config;

import java.nio.charset.StandardCharsets;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ResponseEntityRedisSerializer implements RedisSerializer<ResponseEntity<?>> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public byte[] serialize(ResponseEntity<?> value) throws SerializationException {
        if (value == null) return new byte[0];

        ObjectNode node = mapper.createObjectNode();
        node.put("status", value.getStatusCodeValue());
        node.set("body", mapper.valueToTree(value.getBody()));

        try {
            return mapper.writeValueAsBytes(node);
        } catch (Exception e) {
            throw new SerializationException("Could not serialize ResponseEntity", e);
        }
    }
    
    @Override
    public ResponseEntity<?> deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0) return null;
        try {
            ObjectNode node = (ObjectNode) mapper.readTree(bytes);

            // ✅ Check for null before using
            JsonNode statusNode = node.get("status");
            JsonNode bodyNode = node.get("body");

            if (statusNode == null || bodyNode == null) {
                throw new SerializationException("Missing 'status' or 'body' field in JSON");
            }

            int status = statusNode.asInt();
            Object body = mapper.treeToValue(bodyNode, Object.class);

            return new ResponseEntity<>(body, HttpStatus.valueOf(status));
        } catch (Exception e) {
            throw new SerializationException("Could not deserialize ResponseEntity", e);
        }
    }


//    @Override
//    public ResponseEntity<?> deserialize(byte[] bytes) throws SerializationException {
//        if (bytes == null || bytes.length == 0) return null;
//        try {
//            ObjectNode node = (ObjectNode) mapper.readTree(bytes);
//            int status = node.get("status").asInt();
//            Object body = mapper.treeToValue(node.get("body"), Object.class);
//            return new ResponseEntity<>(body, HttpStatus.valueOf(status));
//        } catch (Exception e) {
//            throw new SerializationException("Could not deserialize ResponseEntity", e);
//        }
//    }
}

