package com.abored.swe.chat_app.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class MessageDeserializer implements Deserializer<Message> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map configs, boolean isKey) {
        Deserializer.super.configure(configs, isKey);
    }

    @Override
    public Message deserialize(String s, byte[] bytes) {
        try {
            return objectMapper.readValue(new String(bytes,"UTF-8"), Message.class);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Error when deserializing Message class");
        } catch (UnsupportedEncodingException e) {
            throw new SerializationException("Unsupported charset");
        }
    }

    @Override
    public void close() {
        Deserializer.super.close();
    }
}
