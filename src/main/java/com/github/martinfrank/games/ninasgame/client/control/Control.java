package com.github.martinfrank.games.ninasgame.client.control;

import com.github.martinfrank.games.ninasgame.client.queue.RabbitQueueServiceImpl;
import com.github.martinfrank.games.ninasgame.client.service.MapLoaderService;
import com.github.martinfrank.games.ninasgame.client.service.NinasGameServerRestService;
import com.github.martinfrank.games.ninasgame.client.model.Model;
import com.github.martinfrank.games.ninasgame.client.view.notification.ViewNotification;
import com.github.martinfrank.games.ninasgame.client.view.notification.ViewNotificationType;
import com.github.martinfrank.games.ninasgame.client.view.notification.ViewReference;
import com.github.martinfrank.ninasgame.model.account.AccountDetails;
import com.github.martinfrank.ninasgame.model.map.Map;
import com.github.martinfrank.ninasgame.model.monster.Monster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.github.martinfrank.games.ninasgame.client.queue.ReceiveLogs.AREA_LISTENER_ID;

@Component
public class Control {

    private static final Logger LOGGER = LoggerFactory.getLogger(Control.class);
    @Autowired
    private NinasGameServerRestService ninasGameServerRestService;

    @Autowired
    private MapLoaderService mapLoaderService;

    @Autowired
    private RabbitQueueServiceImpl rabbitQueueService;

    @Autowired
    private Model model;

    private final List<ViewReference> viewReferences = new ArrayList<>();

    public void addViewReference(ViewReference viewReference) {
        viewReferences.add(viewReference);
    }

    public void startModel() {
        model.start();
    }

    public void stopApp() {
        model.stop();
    }

    public void loadMap() {
    }

    public void goListenToTheBasement() {
        model.getPlayer().setMapName("basement");
        enterMap(model.getPlayer());
    }

    public void goListenToTheWoods() {
        model.getPlayer().setMapName("meadow");
        enterMap(model.getPlayer());
    }



    public void shutdown() {

    }

    public void loadAccountDetails(String user, String pass) {
        LOGGER.debug("load account details");
        AccountDetails accountDetails = ninasGameServerRestService.loadAccountDetails(user, pass);
        notifyViewReferences(new ViewNotification(ViewNotificationType.ACCOUNT_DETAILS, accountDetails));
    }

    private void notifyViewReferences(ViewNotification viewNotification) {
        viewReferences.forEach(v -> v.updateView(viewNotification));
    }

    public void createNewCharacter() {
        //FIXME create new Character
    }

    public void enterMap(Monster player) {
        LOGGER.debug("enter map: {}, mapname: {}", player.getName(), player.getMapName());
        Map map = mapLoaderService.downloadMap(player.getMapName());
        model.startMap(player, map);

        listenToQueue(map.getQueueName());
        notifyViewReferences(new ViewNotification(ViewNotificationType.SHOW_MAP_PANEL, null));
    }

    private void listenToQueue(String queueName) {
        String str = "com.github.martinfrank.games.ninasgame.areaserver.fanout.exchange";
        LOGGER.debug("change topic listening to {}", queueName);
//        rabbitQueueService.removeAllListener(AREA_LISTENER_ID);
        rabbitQueueService.listenToQueue(AREA_LISTENER_ID, queueName);
    }
}
