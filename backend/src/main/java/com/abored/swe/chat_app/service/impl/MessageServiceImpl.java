package com.abored.swe.chat_app.service.impl;

import com.abored.swe.chat_app.entity.MessageEntity;
import com.abored.swe.chat_app.model.Message;
import com.abored.swe.chat_app.repository.MessageRepository;
import com.abored.swe.chat_app.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public Message saveMessage(Message message) {
        MessageEntity messageEntity = MessageEntity.builder()
                .sender(message.getSender())
                .content(message.getContent())
                .time(LocalDateTime.now())
                .build();
        MessageEntity savedEntity = messageRepository.save(messageEntity);
        return Message.builder()
                .sender(savedEntity.getSender())
                .content(savedEntity.getContent())
                .build();
    }

    @Override
    public List<Message> getRecentMessages() {
        Pageable pageable = Pageable.ofSize(10);
        return messageRepository.findAllByOrderByTimeDesc(pageable).stream()
                .map(messageEntity -> {
                    return Message.builder()
                            .sender(messageEntity.getSender())
                            .content(messageEntity.getContent())
                            .build();
                })
                .collect(Collectors.toList());
    }

}
