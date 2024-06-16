package com.abored.swe.chat_app.configuration;

import com.abored.swe.chat_app.model.Message;
import com.abored.swe.chat_app.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import static com.abored.swe.chat_app.constants.AppConstants.CONSUMER_GROUP;
import static com.abored.swe.chat_app.constants.AppConstants.TOPIC;

@Configuration
@Slf4j
public class KafkaConfiguration {

    @Autowired
    private MessageService messageService;

    @Bean
    public NewTopic topic(){
        int partitions = 1;
        short replicationFactor = 1;
        return new NewTopic(TOPIC,partitions,replicationFactor);
    }

    @KafkaListener(groupId = CONSUMER_GROUP,topics = TOPIC)
    public void listener(Message message){
        saveMessageInDB(message);
    }

    private void saveMessageInDB(Message message) {
        Message savedMessage = messageService.saveMessage(message);
        log.info("Message saved in db: {}",savedMessage);
    }
}
