package com.github.martinfrank.games.ninasgame.client.view.notification;

import com.github.martinfrank.ninasgame.model.account.AccountDetails;

public enum ViewNotificationType {

    ACCOUNT_DETAILS (AccountDetails.class), TEST (null), SHOW_MAP_PANEL(null);

    @SuppressWarnings("rawtypes")
    public final Class aClass;

    @SuppressWarnings("rawtypes")
    ViewNotificationType(Class aClass){
        this.aClass = aClass;
    }
}
