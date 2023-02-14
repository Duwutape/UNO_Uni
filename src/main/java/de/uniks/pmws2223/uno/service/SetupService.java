package de.uniks.pmws2223.uno.service;

import de.uniks.pmws2223.uno.model.Card;
import de.uniks.pmws2223.uno.model.Game;
import de.uniks.pmws2223.uno.model.Player;

import static de.uniks.pmws2223.uno.Constants.*;

public class SetupService {

    public Game createGame(CardService cardService, Player player, int numberBots) {

        Game game = new Game().setCurrentPlayer(player)
                .withPlayers(player)
                .setDiscardPile(cardService.createCard())
                .setDirection(CLOCKWISE);

        for (int i = 0; i < numberBots; i++) {
            new Player().setName("bot" + i)
                    .setType(BOT)
                    .setGame(game);
        }

        for (Player play : game.getPlayers()) {
            for (int i = 0; i < NUMBER_CARDS_START; i++) {
                play.withCards(cardService.createCard());
            }

        }

        return game;
    }
}