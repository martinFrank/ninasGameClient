package com.github.martinfrank.games.ninasgame.client.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class TilesetImageWriter {

    private static final Logger LOGGER = LoggerFactory.getLogger(TilesetImageWriter.class);

    public static void saveTilesetImage(String filename, String imageContentAsBase64String) {
        try {
            byte[] data = Base64.getDecoder().decode(imageContentAsBase64String.getBytes());
            File directory = getGameDirectory();
            File tilesetImage = new File(directory, filename);
            LOGGER.debug("saving image to {}", tilesetImage.getAbsolutePath());
            if(!tilesetImage.exists() ){
                tilesetImage.createNewFile();
            }
            Files.write(tilesetImage.toPath(), data);
        } catch (IOException e) {
            LOGGER.debug("could not write tileset image");
            throw new RuntimeException(e);
        }
    }

    private static File getGameDirectory() {
        File userHome = new File(System.getProperty("user.home"));
        File gameDirectory = new File(userHome, "ninasgame");
        if (!gameDirectory.exists()) {
            boolean success = gameDirectory.mkdirs();
            if (!success) {
                LOGGER.debug("could not create ninasgame directory");
            }
        }
        return gameDirectory;
    }

}
