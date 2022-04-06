package se.iths.corkdork.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class MessagePublisher {

    private final RabbitTemplate template;
    Logger logger = LoggerFactory.getLogger(MessagePublisher.class);


    public MessagePublisher(RabbitTemplate template) {
        this.template = template;
    }

    public void sendMessage(String username) {
        logger.info("User created: {}", username);
        CustomMessage customMessage = new CustomMessage(UUID.randomUUID().toString(),
                "User created: " + username, new Date());
        template.convertAndSend(MQConfig.EXCHANGE, MQConfig.ROUTING_KEY, customMessage);
    }
}
