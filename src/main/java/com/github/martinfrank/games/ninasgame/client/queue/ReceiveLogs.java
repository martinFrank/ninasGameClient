package com.github.martinfrank.games.ninasgame.client.queue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ReceiveLogs {

    private static String ROUTING_KEY_USER_IMPORTANT_WARN = "user.important.warn";
    private static String ROUTING_KEY_USER_IMPORTANT_ERROR = "user.important.error";


    @RabbitListener(queues = { BroadcastConfig.FANOUT_QUEUE_1_NAME })
    public void receiveMessageFromFanout1(String message) {
        System.out.println("Received fanout 1 message: " + message);
    }

    @RabbitListener(queues = { BroadcastConfig.FANOUT_QUEUE_2_NAME })
    public void receiveMessageFromFanout2(String message) {
        System.out.println("Received fanout 2 message: " + message);
    }

    @RabbitListener(queues = { BroadcastConfig.TOPIC_QUEUE_1_NAME })
    public void receiveMessageFromTopic1(String message) {
        System.out.println("Received topic 1 (" + BroadcastConfig.BINDING_PATTERN_IMPORTANT + ") message: " + message);
    }

    @RabbitListener(queues = { BroadcastConfig.TOPIC_QUEUE_2_NAME })
    public void receiveMessageFromTopic2(String message) {
        System.out.println("Received topic 2 (" + BroadcastConfig.BINDING_PATTERN_ERROR + ") message: " + message);
    }

}
