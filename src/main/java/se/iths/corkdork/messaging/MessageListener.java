package se.iths.corkdork.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RabbitListener(queues = MQConfig.QUEUE)
    public void listener(CustomMessage message) {

        String listenerMessage = message.getMessage();

        logger.info(listenerMessage);
    }
}
