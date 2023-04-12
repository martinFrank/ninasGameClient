package com.github.martinfrank.games.ninasgame.client.queue;

import com.github.martinfrank.ninasgame.model.map.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
@Component
@RabbitListener(queues = "rabbitmq.queue", id = "listener")
public class RabbitMQReceiver {
    private static final Logger LOGGER = LogManager.getLogger(RabbitMQReceiver.class);

    @RabbitHandler
    public void receiver(Map map) {
        LOGGER.info("Consuming Message with Map: " + map.getWidth()+"/"+map.getHeight());
    }
}