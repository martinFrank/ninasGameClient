package com.github.martinfrank.games.ninasgame.client.model;

import com.github.martinfrank.games.ninasgame.client.control.Control;
import com.github.martinfrank.games.ninasgame.client.util.FileUtil;
import com.github.martinfrank.ninasgame.model.map.Map;
import com.github.martinfrank.ninasgame.model.monster.Monster;
import org.mapeditor.io.TMXMapReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Timer;
import java.util.TimerTask;

@Component
public class Model {
    private final Timer timer;
    private Monster player;

    private Map map;
    private org.mapeditor.core.Map tiledmap;
    private static final Logger LOGGER = LoggerFactory.getLogger(Control.class);

    public Model(){
        timer = new Timer();
    }

    public void start() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateModel();
            }
        }, 0, 100);
    }

    public void stop() {
        timer.cancel();
    }

    private void updateModel() {
    }

    public void startMap(Monster player, Map map) {
        this.player = player;
        this.map = map;
        try {
            TMXMapReader mapReader = new TMXMapReader();
            this.tiledmap = mapReader.readMap(FileUtil.getFile(map.getMapFilename()).getAbsolutePath());
            LOGGER.debug("map loaded successfully...");
        }catch (Exception e){
            LOGGER.error("exception during create map",e);
        }
    }

    public org.mapeditor.core.Map getMap() {
        return tiledmap;
    }

    public Monster getPlayer() {
        return player;
    }

    public void setPlayer(Monster player) {
        this.player = player;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public org.mapeditor.core.Map getTiledmap() {
        return tiledmap;
    }

    public void setTiledmap(org.mapeditor.core.Map tiledmap) {
        this.tiledmap = tiledmap;
    }
}
