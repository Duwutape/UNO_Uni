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

            if (game.getCurrentPlayer().getCards() == null) {
                game.setHasWon(game.getCurrentPlayer());
            }

            switch (card.getValue()) {
                case REVERSE -> {
                    setDirection();
                    nextPlayer();
                    endTurn();
                }
                case SKIP -> skipPlayer();
                case DRAW -> drawTwo();
                case BLACK -> {
                }
                default -> {
                    nextPlayer();
                    endTurn();
                }
            }
        }
    }

    public Card drawCard() {
        return cardService.createCard();
    }

    public void drawCard(Player player) {
        player.withCards(drawCard());
    }

    public void nextPlayer() {
        List<Player> players = game.getPlayers();
        int index = players.indexOf(game.getCurrentPlayer());

        System.out.println(index);
        System.out.println(players.size());
        int newPlayer;
        if (game.getDirection().equals(CLOCKWISE)) {
            newPlayer = (index + 1) % players.size();
        } else {
            newPlayer = (index + players.size() - 1) % players.size();
        }
        game.setCurrentPlayer(players.get(newPlayer));
    }

    private void setDirection(){
        String currentDirection = game.getDirection();

        if(currentDirection.equals(CLOCKWISE)) {
            game.setDirection(COUNTER_CLOCKWISE);
        } else {
            game.setDirection(CLOCKWISE);
        }
    }

    private void skipPlayer() {
        nextPlayer();
        nextPlayer();
        endTurn();
    }

    private void drawTwo() {
        nextPlayer();
        game.getCurrentPlayer().withCards(drawCard(), drawCard());
        nextPlayer();
        endTurn();
    }

    public void endTurn() {
        if (game.getCurrentPlayer().getType().equals(BOT)) {
            final BotService botService = new BotService(game, game.getCurrentPlayer());
            botService.playWithDelay();
        }
    }
}
