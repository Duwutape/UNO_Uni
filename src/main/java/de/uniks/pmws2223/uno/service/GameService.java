package de.uniks.pmws2223.uno.service;

import de.uniks.pmws2223.uno.model.Card;
import de.uniks.pmws2223.uno.model.Game;
import de.uniks.pmws2223.uno.model.Player;

import java.util.List;

import static de.uniks.pmws2223.uno.Constants.*;

public class GameService {

    private final CardService cardService = new CardService();
    private Game game;

    public GameService(Game game) {
        this.game = game;
    }

    public void playCard(Game game, Card card) {
        this.game = game;
        Card discardPile = game.getDiscardPile();
        if (card.getValue().equals(discardPile.getValue()) || card.getColor().equals(discardPile.getColor())) {
            game.setDiscardPile(card);
            card.getPlayer().withoutCards(card);

            switch (card.getValue()) {
                case REVERSE -> game.setDirection(COUNTER_CLOCKWISE);
                case SKIP -> skipPlayer();
                case DRAW -> drawTwo();
                default -> nextPlayer();
            }
        } else if (card.getColor().equals(BLACK)) {
            game.setDiscardPile(card);
            card.getPlayer().withoutCards(card);
        }
    }

    public Card drawCard(){
        return cardService.createCard();
    }

    private void drawCard(Player player) {
        player.withCards(drawCard());
    }

    public void nextPlayer() {
        List<Player> players = game.getPlayers();
        int index = players.indexOf(game.getCurrentPlayer());

        if (game.getDirection().equals(CLOCKWISE)) {
            game.setCurrentPlayer(players.get((index + 1) % players.size()));
        } else {
            game.setCurrentPlayer(players.get((index + players.size() - 1) % players.size()));
        }
    }

    private void skipPlayer() {
        nextPlayer();
        nextPlayer();
    }

    private void drawTwo() {
        nextPlayer();
        game.getCurrentPlayer().withCards(drawCard(), drawCard());
        nextPlayer();
    }
}
