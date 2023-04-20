package com.github.martinfrank.games.ninasgame.client.service;

import com.github.martinfrank.ninasgame.model.account.AccountDetails;
import com.github.martinfrank.ninasgame.model.file.File;
import com.github.martinfrank.ninasgame.model.map.Map;


public interface NinasGameServerRestService {

    Map loadMap(String mapName);

    AccountDetails loadAccountDetails(String user, String pass);

    File loadFile(String filename);


}
