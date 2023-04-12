package com.github.martinfrank.games.ninasgame.client.control;

import com.github.martinfrank.games.ninasgame.client.queue.BroadcastConfig;
import com.github.martinfrank.games.ninasgame.client.queue.RabbitQueueServiceImpl;
import com.github.martinfrank.games.ninasgame.client.service.NinasGameServerRestService;
import com.github.martinfrank.games.ninasgame.client.model.Model;
import com.github.martinfrank.ninasgame.model.map.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.github.martinfrank.games.ninasgame.client.queue.ReceiveLogs.AREA_LISTENER_ID;

@Component
public class Control {

    @Autowired
    private final NinasGameServerRestService ninasGameServerRestService;

    @Autowired
    private final RabbitQueueServiceImpl rabbitQueueService;

    @Autowired
    private final Model controllable;
    public Control(NinasGameServerRestService ninasGameServerRestService, RabbitQueueServiceImpl rabbitQueueService, Model controllable) {
        this.ninasGameServerRestService = ninasGameServerRestService;
        this.rabbitQueueService = rabbitQueueService;
        this.controllable = controllable;
    }

    public void startModel() {
        controllable.start();
    }

    public void stopApp() {
        controllable.stop();
    }

    public void loadMap() {
        System.out.println("load map....");
        Map map = ninasGameServerRestService.loadMap();
        System.out.println("loaded map: "+map);
    }

    public void goListenToTheBasement() {
        rabbitQueueService.removeQueueFromListener(AREA_LISTENER_ID, BroadcastConfig.FANOUT_QUEUE_1_NAME);
        rabbitQueueService.addQueueToListener(AREA_LISTENER_ID,  BroadcastConfig.FANOUT_QUEUE_2_NAME);
    }

    public void goListenToTheWoods() {
        rabbitQueueService.removeQueueFromListener(AREA_LISTENER_ID, BroadcastConfig.FANOUT_QUEUE_2_NAME);
        rabbitQueueService.addQueueToListener(AREA_LISTENER_ID,  BroadcastConfig.FANOUT_QUEUE_1_NAME);
    }
}
