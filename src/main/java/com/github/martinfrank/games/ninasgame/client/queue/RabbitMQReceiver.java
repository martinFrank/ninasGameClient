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
    private static Logger logger = LogManager.getLogger(RabbitMQReceiver.class.toString());

    @RabbitHandler
    public void receiver(Map map) {
        logger.info("Consuming Message with Map: " + map.getWidth()+"/"+map.getHeight());
    }
}