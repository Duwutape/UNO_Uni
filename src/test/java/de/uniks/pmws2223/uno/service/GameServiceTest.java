package de.uniks.pmws2223.uno.service;

import de.uniks.pmws2223.uno.model.Card;
import de.uniks.pmws2223.uno.model.Game;
import de.uniks.pmws2223.uno.model.Player;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static de.uniks.pmws2223.uno.Constants.*;
import static org.junit.Assert.assertEquals;

public class GameServiceTest {

    private final RandomService randomService = new RandomService();
    private final Game game = new Game();
    private final GameService gameService = new GameService(randomService, game);

    @Test
    public void playCard() {
        Player player = new Player().setGame(game);
        game.setDiscardPile(new Card(THREE, YELLOW)).setCurrentPlayer(player);

        /* Before playing the card the player should have one card on their hand.
           The card should be played because the value matches with the card on the discard pile.
           If the card was played the player should not have this card anymore and the card on
           top of the discard pile should have the value and color of the played card. */
        Card card1 = new Card(THREE, RED);
        player.withCards(card1);

        assertEquals(List.of(card1), player.getCards());
        gameService.playCard(card1);
        assertEquals(List.of(), player.getCards());
        assertEquals(THREE, game.getDiscardPile().getValue());
        assertEquals(RED, game.getDiscardPile().getColor());

        /* Before playing the card the player should have one card on their hand.
           The card should be played because the color matches with the card on the discard pile.
           If the card was played the player should not have this card anymore and the card on
           top of the discard pile should have the value and color of the played card. */
        Card card2 = new Card(ONE, RED);
        player.withCards(card2);

        assertEquals(List.of(card2), player.getCards());
        gameService.playCard(card2);
        assertEquals(List.of(), player.getCards());
        assertEquals(ONE, game.getDiscardPile().getValue());
        assertEquals(RED, game.getDiscardPile().getColor());

        /* Before playing the card the player should have one card on their hand.
           The card should not be played because neither the color nor the value matches with the
           card on the discard pile.
           So after the attempt to play this card the card on top of the discard pile should be the
           same as before and the player still has the card on their hand. */
        Card card3 = new Card(NINE, GREEN);
        player.withCards(card3);

        assertEquals(List.of(card3), player.getCards());
        gameService.playCard(card3);
        assertEquals(List.of(card3), player.getCards());
        assertEquals(ONE, game.getDiscardPile().getValue());
        assertEquals(RED, game.getDiscardPile().getColor());
    }

    @Test
    public void playDrawTwo() {
        Player player = new Player().setGame(game).setType(HUMAN);
        Player bot1 = new Player().setGame(game).setType(BOT);
        Player bot2 = new Player().setGame(game).setType(BOT);
        game.setDiscardPile(new Card(THREE, YELLOW)).setCurrentPlayer(player).setDirection(CLOCKWISE);

        /* Before playing the card the player should have two cards on their hand.
           The bots should have no card on their hand.
           After playing a draw two card the player should have one card on their hand, the bot left of
           the player (bot1) should get two cards and will be skipped.
           So the bot left of this bot (bot2) will be in turn. This bot should have no cards on their hand. */
        Card card1 = new Card(DRAW, YELLOW);
        Card card2 = new Card(EIGHT, RED);
        player.withCards(card1, card2);

        assertEquals(List.of(card1, card2), player.getCards());
        assertEquals(List.of(), bot1.getCards());
        assertEquals(List.of(), bot2.getCards());
        gameService.playCard(card1);
        assertEquals(List.of(card2), player.getCards());
        assertEquals(2, bot1.getCards().size());
        assertEquals(List.of(), bot2.getCards());
        assertEquals(bot2, game.getCurrentPlayer());
    }

    @Test
    public void playReverse() {
        Player player = new Player().setGame(game).setType(HUMAN);
        Player bot1 = new Player().setGame(game).setType(BOT);
        Player bot2 = new Player().setGame(game).setType(BOT);
        game.setDiscardPile(new Card(THREE, YELLOW)).setCurrentPlayer(player).setDirection(CLOCKWISE);

        /* Before playing the card the player should have two cards on their hand.
           After playing a reverse card the player should have one card on their hand and the bot right of
           the player (bot2) should be in turn and the direction should be counter-clockwise. */
        Card card1 = new Card(REVERSE, YELLOW);
        Card card2 = new Card(EIGHT, RED);
        player.withCards(card1, card2);

        assertEquals(List.of(card1, card2), player.getCards());
        gameService.playCard(card1);
        assertEquals(List.of(card2), player.getCards());
        assertEquals(bot2, game.getCurrentPlayer());
        assertEquals(COUNTER_CLOCKWISE, game.getDirection());

        /* Now the player is in turn again and plays another reverse card.
           Before playing the card the player should have two cards on their hand.
           After playing this card the player should have one card on their hand and the bot left of
           the player (bot1) should be in turn and the direction should be clockwise again. */
        game.setCurrentPlayer(player);
        Card card3 = new Card(REVERSE, YELLOW);
        player.withCards(card3);

        assertEquals(List.of(card2, card3), player.getCards());
        gameService.playCard(card3);
        assertEquals(List.of(card2), player.getCards());
        assertEquals(bot1, game.getCurrentPlayer());
        assertEquals(CLOCKWISE, game.getDirection());
    }

    @Test
    public void playSkip() {
        Player player = new Player().setGame(game).setType(HUMAN);
        Player bot1 = new Player().setGame(game).setType(BOT);
        Player bot2 = new Player().setGame(game).setType(BOT);
        game.setDiscardPile(new Card(THREE, YELLOW)).setCurrentPlayer(player).setDirection(CLOCKWISE);

        /* Before playing the card the player should have two cards on their hand.
           The bots should have no card on their hand.
           After playing a skip card the player should have one card on their hand, the bot left of
           the player (bot1) should be skipped.
           So the bot left of this bot (bot2) will be in turn. */
        Card card1 = new Card(SKIP, YELLOW);
        Card card2 = new Card(EIGHT, RED);
        player.withCards(card1, card2);

        assertEquals(List.of(card1, card2), player.getCards());
        assertEquals(List.of(), bot1.getCards());
        assertEquals(List.of(), bot1.getCards());
        gameService.playCard(card1);
        assertEquals(List.of(card2), player.getCards());
        assertEquals(bot2, game.getCurrentPlayer());
    }

    @Test
    public void playWild() {
        Player player = new Player().setGame(game);
        game.setDiscardPile(new Card(THREE, YELLOW)).setCurrentPlayer(player);

        /* Before playing the card the player should have one card on their hand.
           The card should be played because a wild card can be played on anything.
           After playing this card the player should have only one card on their hand
           and on top of the discard pile should be this wild card.
           Because the process of choosing a new color works through the gui I only can simulate it here.
           After choosing a color the card on the discard pile will be switched with a new wild card with
           the chosen color. In the first test I will simulate choosing red. */
        Card card1 = new Card(WILD, BLACK);
        Card card2 = new Card(EIGHT, RED);
        player.withCards(card1, card2);

        assertEquals(List.of(card1, card2), player.getCards());
        gameService.playCard(card1);
        assertEquals(List.of(card2), player.getCards());
        assertEquals(WILD, game.getDiscardPile().getValue());
        assertEquals(BLACK, game.getDiscardPile().getColor());
        game.setDiscardPile(new Card(WILD, RED));
        assertEquals(WILD, game.getDiscardPile().getValue());
        assertEquals(RED, game.getDiscardPile().getColor());

        /* Now this test will be repeated three times to test that a black card can be played on every other
           card.
           Now it will be played on a red card.*/
        Card card3 = new Card(WILD, BLACK);
        player.withCards(card3);

        assertEquals(List.of(card2, card3), player.getCards());
        gameService.playCard(card3);
        assertEquals(List.of(card2), player.getCards());
        assertEquals(WILD, game.getDiscardPile().getValue());
        assertEquals(BLACK, game.getDiscardPile().getColor());
        game.setDiscardPile(new Card(WILD, GREEN));
        assertEquals(WILD, game.getDiscardPile().getValue());
        assertEquals(GREEN, game.getDiscardPile().getColor());

        /* Now it will be played on a green card.*/
        Card card4 = new Card(WILD, BLACK);
        player.withCards(card4);

        assertEquals(List.of(card2, card4), player.getCards());
        gameService.playCard(card4);
        assertEquals(List.of(card2), player.getCards());
        assertEquals(WILD, game.getDiscardPile().getValue());
        assertEquals(BLACK, game.getDiscardPile().getColor());
        game.setDiscardPile(new Card(WILD, BLUE));
        assertEquals(WILD, game.getDiscardPile().getValue());
        assertEquals(BLUE, game.getDiscardPile().getColor());

        /* At last, it will be played on a blue card.*/
        Card card5 = new Card(WILD, BLACK);
        player.withCards(card5);

        assertEquals(List.of(card2, card5), player.getCards());
        gameService.playCard(card5);
        assertEquals(List.of(card2), player.getCards());
        assertEquals(WILD, game.getDiscardPile().getValue());
        assertEquals(BLACK, game.getDiscardPile().getColor());
        game.setDiscardPile(new Card(WILD, BLUE));
        assertEquals(WILD, game.getDiscardPile().getValue());
        assertEquals(BLUE, game.getDiscardPile().getColor());
    }

    @Test
    public void winGame() {
        Player player = new Player().setGame(game);
        game.setDiscardPile(new Card(THREE, YELLOW)).setCurrentPlayer(player);

        /* Before playing the card the player should have one card on their hand.
           After playing the card the player does not have any more cards and wins
           the game. */
        Card card1 = new Card(THREE, RED);
        player.withCards(card1);

        assertEquals(List.of(card1), player.getCards());
        gameService.playCard(card1);
        assertEquals(List.of(), player.getCards());
        assertEquals(player, game.getHasWon());
    }
}
