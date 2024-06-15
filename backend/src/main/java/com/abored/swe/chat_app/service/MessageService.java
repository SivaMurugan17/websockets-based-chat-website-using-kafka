package com.abored.swe.chat_app.service;

import com.abored.swe.chat_app.model.Message;

public interface MessageService {
    Message saveMessage(Message message);
}
