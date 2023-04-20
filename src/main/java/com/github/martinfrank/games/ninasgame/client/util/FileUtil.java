package com.github.martinfrank.games.ninasgame.client.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FileUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    public static boolean exists(String filename){
        File file = new File(FileUtil.getGameDirectory(), filename);
        return file.exists();
    }


    static File getGameDirectory() {
        File userHome = new File(System.getProperty("user.home"));
        File gameDirectory = new File(userHome, "ninasgame");
        if (!gameDirectory.exists()) {
            boolean success = gameDirectory.mkdirs();
            if (!success) {
                LOGGER.debug("could not create ninasgame directory");
                //FIXME Throw exception!
            }
        }
        return gameDirectory;
    }

    public static List<File> getFilesFromGameDirs(){
        File gameDir = getGameDirectory();
        return Arrays.asList(gameDir.listFiles());
    }

    public static File getFile(String filename) {
        return new File(getGameDirectory(), filename);
    }
}
