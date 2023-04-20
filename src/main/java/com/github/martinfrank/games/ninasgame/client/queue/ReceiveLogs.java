package com.github.martinfrank.games.ninasgame.client.queue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ReceiveLogs {

    private static final Logger LOGGER = LogManager.getLogger(RabbitMQSender.class);

    public static final String AREA_LISTENER_ID = "area-listener";


//    @RabbitListener(queues = BroadcastConfig.FANOUT_QUEUE_1_NAME, id = AREA_LISTENER_ID)
    @RabbitListener(id = AREA_LISTENER_ID)
    public void receiveMessageFromFanout1(String message) {
        LOGGER.info("Received fanout message: {}", message);
    }

}
