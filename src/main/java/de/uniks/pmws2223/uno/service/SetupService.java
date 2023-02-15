package de.uniks.pmws2223.uno.service;

import de.uniks.pmws2223.uno.Constants;
import de.uniks.pmws2223.uno.model.Card;
import de.uniks.pmws2223.uno.model.Game;
import de.uniks.pmws2223.uno.model.Player;

import static de.uniks.pmws2223.uno.Constants.*;

public class SetupService {

    public Game createGame(RandomService randomService, Player player, int numberBots) {

        Game game = new Game().setCurrentPlayer(player)
                .withPlayers(player)
                .setDiscardPile(randomService.createCard())
                .setDirection(CLOCKWISE);

        if (game.getDiscardPile().getColor().equals(BLACK)) {
            int color = randomService.chooseColor();
            game.setDiscardPile(new Card(WILD, COLORS.get(color)));
        }

        for (int i = 0; i < numberBots; i++) {
            new Player().setName(NAMES.get(i))
                    .setType(BOT)
                    .setGame(game);
        }

        for (Player players : game.getPlayers()) {
            for (int i = 1; i <= NUMBER_CARDS_START; i++) {
                players.withCards(randomService.createCard());
            }
        }

        return game;
    }
}
