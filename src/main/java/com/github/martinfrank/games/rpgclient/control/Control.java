package com.github.martinfrank.games.rpgclient.control;

import com.github.martinfrank.games.rpgclient.model.Model;
import com.github.martinfrank.games.rpgclient.service.RpgService;
import com.github.martinfrank.ninasgame.model.map.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Control {

    @Autowired
    private final RpgService rpgService;

    @Autowired
    private final Model controllable;
    public Control(RpgService rpgService, Model controllable) {
        this.rpgService = rpgService;
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
        Map map = rpgService.loadMap();
        System.out.println("loaded map: "+map);
    }

    public void goToWood() {

    }
}
