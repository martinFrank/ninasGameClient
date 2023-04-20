package com.github.martinfrank.games.ninasgame.client.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class FileWriter {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileWriter.class);

    public static void saveFile(String filename, String imageContentAsBase64String) {
        try {
            byte[] data = Base64.getDecoder().decode(imageContentAsBase64String.getBytes());
            File directory = FileUtil.getGameDirectory();
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


}
