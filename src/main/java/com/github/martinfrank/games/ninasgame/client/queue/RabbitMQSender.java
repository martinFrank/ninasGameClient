package com.github.martinfrank.games.ninasgame.client.queue;

import com.github.martinfrank.ninasgame.model.map.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class RabbitMQSender {
    @Autowired
    private AmqpTemplate rabbitTemplate;
    @Autowired
    private Queue queue;
    private static Logger logger = LogManager.getLogger(RabbitMQSender.class.toString());
    public void send(Map map) {
        rabbitTemplate.convertAndSend(queue.getName(), map);
        logger.info("Sending Message to the Queue : " + map.toString());
    }
}