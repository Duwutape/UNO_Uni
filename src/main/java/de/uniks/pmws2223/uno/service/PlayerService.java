package de.uniks.pmws2223.uno.service;

import de.uniks.pmws2223.uno.model.Player;

public class PlayerService {

    public Player createPlayer(String name) {
        return new Player().setName(name);
    }
}
