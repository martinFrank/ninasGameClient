package com.github.martinfrank.games.ninasgame.client.view.notification;

public class ViewNotification {

    public final ViewNotificationType type;
    public final Object object;

    public ViewNotification(ViewNotificationType type, Object object) {
        this.type = type;
        this.object = object;
    }

}
