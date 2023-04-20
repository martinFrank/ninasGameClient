package com.github.martinfrank.games.ninasgame.client.service;

import com.github.martinfrank.games.ninasgame.client.util.FileUtil;
import com.github.martinfrank.games.ninasgame.client.util.FileWriter;
import com.github.martinfrank.ninasgame.model.file.File;
import com.github.martinfrank.ninasgame.model.map.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MapLoaderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapLoaderService.class);
    @Autowired
    private NinasGameServerRestService ninasGameServerRestService;

    public Map downloadMap(String mapName){
        LOGGER.debug("downloadMapResources");

        LOGGER.debug("download map {}", mapName);
        Map map = ninasGameServerRestService.loadMap(mapName);
        LOGGER.debug("map is downloaded, this map requires this file: {}", map.getRequiredFilenames());

        for(String requiredFilename: map.getRequiredFilenames()){
            boolean isInInventory = FileUtil.exists(requiredFilename);
            LOGGER.debug("required file {} is already in inventory={}", requiredFilename, isInInventory);
            if(!isInInventory){
                LOGGER.debug("required file {} not there, we download it...", requiredFilename);
                File file = ninasGameServerRestService.loadFile(requiredFilename);

                LOGGER.debug("write file {} to local storage", requiredFilename);
                FileWriter.saveFile(file.getFilename(), file.getFileContentAsBase64String());
            }
        }
        return map;
    }

}
