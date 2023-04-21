package com.github.martinfrank.games.ninasgame.client.queue;

public interface RabbitQueueService {

//        void addNewQueue(String queueName, String exchangeName, String routingKey);

    void listenToQueue(String listenerId, String queueName);

    void removeQueueFromListener(String listenerId, String queueName);

    void removeAllListener(String listenerId);

//        Boolean checkQueueExistOnListener(String listenerId, String queueName);

}
