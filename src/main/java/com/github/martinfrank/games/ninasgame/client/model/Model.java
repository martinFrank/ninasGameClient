package com.github.martinfrank.games.ninasgame.client.model;

import org.springframework.stereotype.Component;

import java.util.Timer;
import java.util.TimerTask;

@Component
public class Model {
    private final Timer timer;

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

}
