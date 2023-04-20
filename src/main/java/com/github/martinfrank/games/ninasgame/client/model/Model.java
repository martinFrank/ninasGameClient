package com.github.martinfrank.games.ninasgame.client.model;

import com.github.martinfrank.games.ninasgame.client.control.Control;
import com.github.martinfrank.games.ninasgame.client.util.FileUtil;
import com.github.martinfrank.ninasgame.model.map.Map;
import com.github.martinfrank.ninasgame.model.monster.Monster;
import org.mapeditor.io.TMXMapReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class Model {
    private final Timer timer;
    private Monster player;
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

    public void start(Monster player, Map map) {
        this.player = player;
        try {
            TMXMapReader mapReader = new TMXMapReader();
            this.tiledmap = mapReader.readMap(FileUtil.getFile(map.getMapFilename()).getAbsolutePath());
        }catch (Exception e){
            LOGGER.error("exception during create map",e);
        }
        LOGGER.debug("map loaded successfully...");
    }

    public org.mapeditor.core.Map getMap() {
        return tiledmap;
    }
}
