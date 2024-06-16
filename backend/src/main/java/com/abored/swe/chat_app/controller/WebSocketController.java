package com.abored.swe.chat_app.controller;

import com.abored.swe.chat_app.model.Message;
import com.abored.swe.chat_app.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import static com.abored.swe.chat_app.constants.AppConstants.TOPIC;

@Controller
@Slf4j
public class WebSocketController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private KafkaTemplate<String,Message> template;

    @MessageMapping("/backendInput")
    @SendTo("/topic/backendOutput")
    public Message sendMessage(@Payload Message message){
        template.send(TOPIC,message);
        log.info("Message sent to Kafka");
        return message;
    }

}
