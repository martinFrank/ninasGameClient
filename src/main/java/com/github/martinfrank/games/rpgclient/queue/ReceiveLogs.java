package com.github.martinfrank.games.rpgclient.queue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import static com.github.martinfrank.games.rpgclient.queue.BroadcastConfig.BINDING_PATTERN_ERROR;
import static com.github.martinfrank.games.rpgclient.queue.BroadcastConfig.BINDING_PATTERN_IMPORTANT;
import static com.github.martinfrank.games.rpgclient.queue.BroadcastConfig.FANOUT_QUEUE_1_NAME;
import static com.github.martinfrank.games.rpgclient.queue.BroadcastConfig.FANOUT_QUEUE_2_NAME;
import static com.github.martinfrank.games.rpgclient.queue.BroadcastConfig.TOPIC_QUEUE_1_NAME;
import static com.github.martinfrank.games.rpgclient.queue.BroadcastConfig.TOPIC_QUEUE_2_NAME;

@Component
public class ReceiveLogs {

    private static String ROUTING_KEY_USER_IMPORTANT_WARN = "user.important.warn";
    private static String ROUTING_KEY_USER_IMPORTANT_ERROR = "user.important.error";


    @RabbitListener(queues = { FANOUT_QUEUE_1_NAME })
    public void receiveMessageFromFanout1(String message) {
        System.out.println("Received fanout 1 message: " + message);
    }

    @RabbitListener(queues = { FANOUT_QUEUE_2_NAME })
    public void receiveMessageFromFanout2(String message) {
        System.out.println("Received fanout 2 message: " + message);
    }

    @RabbitListener(queues = { TOPIC_QUEUE_1_NAME })
    public void receiveMessageFromTopic1(String message) {
        System.out.println("Received topic 1 (" + BINDING_PATTERN_IMPORTANT + ") message: " + message);
    }

    @RabbitListener(queues = { TOPIC_QUEUE_2_NAME })
    public void receiveMessageFromTopic2(String message) {
        System.out.println("Received topic 2 (" + BINDING_PATTERN_ERROR + ") message: " + message);
    }

}
