package de.uniks.pmws2223.uno.service;

import de.uniks.pmws2223.uno.model.Card;
import de.uniks.pmws2223.uno.model.Game;
import de.uniks.pmws2223.uno.model.Player;

import java.util.List;

import static de.uniks.pmws2223.uno.Constants.*;

public class GameService {

    private final RandomService randomService;
    private final Game game;

    public GameService(RandomService randomService, Game game) {
        this.randomService = randomService;
        this.game = game;
    }

    public void playCard(Card card) {

        if (card.getValue().equals(game.getDiscardPile().getValue())
                || card.getColor().equals(game.getDiscardPile().getColor())
                || card.getColor().equals(BLACK)) {

            game.setDiscardPile(card);
            card.getPlayer().withoutCards(card);
            System.out.println(game.getCurrentPlayer().getCards().size());

            if (game.getCurrentPlayer().getCards().size() == 0) {
                game.setHasWon(game.getCurrentPlayer());
            } else {

                switch (card.getValue()) {
                    case REVERSE -> {
                        setDirection();
                        if (game.getPlayers().size() >= 3) {
                            nextPlayer();
                        }
                        endTurn();
                    }
                    case SKIP -> {
                        skipPlayer();
                        endTurn();
                    }
                    case DRAW -> {
                        drawTwo();
                        endTurn();
                    }
                    case WILD -> {
                    }
                    default -> {
                        nextPlayer();
                        endTurn();
                    }
                }
            }
        }
    }

    public Card drawCard() {
        return randomService.createCard();
    }

    public void drawCard(Player player) {
        player.withCards(drawCard());
    }

    public void nextPlayer() {
        List<Player> players = game.getPlayers();
        int index = players.indexOf(game.getCurrentPlayer());

        int newPlayer;
        if (game.getDirection().equals(CLOCKWISE)) {
            newPlayer = (index + 1) % players.size();
        } else {
            newPlayer = (index + players.size() - 1) % players.size();
        }
        game.setCurrentPlayer(players.get(newPlayer));
    }

    private void setDirection() {
        String currentDirection = game.getDirection();

        if (currentDirection.equals(CLOCKWISE)) {
            game.setDirection(COUNTER_CLOCKWISE);
        } else {
            game.setDirection(CLOCKWISE);
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

    public void endTurn() {
        if (game.getCurrentPlayer().getType().equals(BOT)) {
            final BotService botService = new BotService(randomService, game, game.getCurrentPlayer());
            botService.playWithDelay();
        }
    }
}
