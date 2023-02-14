package de.uniks.pmws2223.uno.service;

import de.uniks.pmws2223.uno.model.Card;
import de.uniks.pmws2223.uno.model.Game;
import de.uniks.pmws2223.uno.model.Player;
import javafx.application.Platform;

import java.util.Timer;
import java.util.TimerTask;

import static de.uniks.pmws2223.uno.Constants.BLACK;

public class BotService {
    private final Game game;
    private final Player currentPlayer;
    private final GameService gameService;
    private boolean playedCard = false;

    public BotService(Game game, Player currentPlayer) {
        this.game = game;
        this.currentPlayer = currentPlayer;
        this.gameService = new GameService(game);
    }

    public void playWithDelay() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    playRound();
                    timer.cancel();
                });
            }
        }, 2000);
        //TODO change timer to 2000
    }

    public void playRound() {

        playCard();
        if (!playedCard) {
            gameService.drawCard(currentPlayer);
            playCard();
            if (!playedCard) {
                gameService.nextPlayer();
                gameService.endTurn();
            }
        }
    }

    private void playCard() {
        //TODO bot plays wild
        for (Card card : currentPlayer.getCards()) {
            if (card.getValue().equals(game.getDiscardPile().getValue())
                    || card.getColor().equals(game.getDiscardPile().getColor())
                    || card.getColor().equals(BLACK)) {
                gameService.playCard(card);
                playedCard = true;
            }
        }
    }
}