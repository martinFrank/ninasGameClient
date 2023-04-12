package com.github.martinfrank.games.ninasgame.client.queue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class RabbitQueueServiceImpl implements RabbitQueueService {

    private static final Logger LOGGER = LogManager.getLogger(RabbitMQReceiver.class);

    @Autowired
    private RabbitAdmin rabbitAdmin;
    @Autowired
    private RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry;

//    @Override
//    public void addNewQueue(String queueName, String exchangeName, String routingKey) {
//        Queue queue = new Queue(queueName, true, false, false);
//        Binding binding = new Binding(
//                queueName,
//                Binding.DestinationType.QUEUE,
//                exchangeName,
//                routingKey,
//                null
//        );
//        rabbitAdmin.declareQueue(queue);
//        rabbitAdmin.declareBinding(binding);
//        this.addQueueToListener(exchangeName,queueName);
//    }

    @Override
    public void addQueueToListener(String listenerId, String queueName) {
        LOGGER.info("adding queue : " + queueName + " to listener with id : " + listenerId);
        if (!checkQueueExistOnListener(listenerId,queueName)) {
            this.getMessageListenerContainerById(listenerId).addQueueNames(queueName);
            LOGGER.info("queue ");
        } else {
            LOGGER.info("given queue name : " + queueName + " not exist on given listener id : " + listenerId);
        }
    }

    @Override
    public void removeQueueFromListener(String listenerId, String queueName) {
        LOGGER.info("removing queue : " + queueName + " from listener : " + listenerId);
        if (checkQueueExistOnListener(listenerId,queueName)) {
            this.getMessageListenerContainerById(listenerId).removeQueueNames(queueName);
            LOGGER.info("deleting queue from rabbit management");
//            this.rabbitAdmin.deleteQueue(queueName);
        } else {
            LOGGER.info("given queue name : " + queueName + " not exist on given listener id : " + listenerId);
        }
    }

//    @Override
    private Boolean checkQueueExistOnListener(String listenerId, String queueName) {
        try {
            LOGGER.info("checking queueName : " + queueName + " exist on listener id : " + listenerId);
            LOGGER.info("getting queueNames");
            String[] queueNames = this.getMessageListenerContainerById(listenerId).getQueueNames();
            LOGGER.info("queueNames : " + Arrays.asList(queueNames));
            if (queueNames != null) {
                LOGGER.info("checking " + queueName + " exist on active queues");
                for (String name : queueNames) {
                    LOGGER.info("name : " + name + " with checking name : " + queueName);
                    if (name.equals(queueName)) {
                        LOGGER.info("queue name exist on listener, returning true");
                        return Boolean.TRUE;
                    }
                }
                return Boolean.FALSE;
            } else {
                LOGGER.info("there is no queue exist on listener");
                return Boolean.FALSE;
            }
        } catch (Exception e) {
            LOGGER.error("Error on checking queue exist on listener");
            LOGGER.error("error message : " + e.getMessage());
            e.printStackTrace();
//            LOGGER.error("trace : " + e.printStackTrace(););
            return Boolean.FALSE;
        }
    }

    private AbstractMessageListenerContainer getMessageListenerContainerById(String listenerId) {
        LOGGER.info("getting message listener container by id : " + listenerId);
        return ((AbstractMessageListenerContainer) this.rabbitListenerEndpointRegistry
                .getListenerContainer(listenerId)
        );
    }
}
