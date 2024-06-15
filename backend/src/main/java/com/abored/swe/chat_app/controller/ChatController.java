package com.abored.swe.chat_app.controller;

import com.abored.swe.chat_app.model.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/backendInput")
    @SendTo("/topic/backendOutput")
    public Message sendMessage(@Payload Message message){
        return message;
    }

}
