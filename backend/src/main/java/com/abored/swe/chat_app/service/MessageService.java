package com.abored.swe.chat_app.service;

import com.abored.swe.chat_app.model.Message;

import java.util.List;

public interface MessageService {
    Message saveMessage(Message message);

    List<Message> getRecentMessages();
}
