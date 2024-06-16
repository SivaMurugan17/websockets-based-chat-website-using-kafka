package com.abored.swe.chat_app.configuration;

import com.abored.swe.chat_app.model.Message;
import com.abored.swe.chat_app.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
@Slf4j
public class KafkaConfiguration {

    @Autowired
    private MessageService messageService;

    @Bean
    public NewTopic topic(){
        int partitions = 1;
        short replicationFactor = 1;
        return new NewTopic("global",partitions,replicationFactor);
    }

    @KafkaListener(groupId = "chatgroup",topics = "global")
    public void listener(Message message){
        Message savedMessage = messageService.saveMessage(message);
        log.info("Message saved in db: {}",savedMessage);
    }
}
