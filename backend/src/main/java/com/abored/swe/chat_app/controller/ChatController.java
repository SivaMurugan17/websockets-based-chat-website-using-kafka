package com.abored.swe.chat_app.controller;

import com.abored.swe.chat_app.model.Message;
import com.abored.swe.chat_app.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class ChatController {

    @Autowired
    private MessageService messageService;

    @MessageMapping("/backendInput")
    @SendTo("/topic/backendOutput")
    public Message sendMessage(@Payload Message message){
        Message savedMessage = messageService.saveMessage(message);
        log.info("Message saved: {}",savedMessage);
        return message;
    }

}
