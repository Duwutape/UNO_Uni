package de.uniks.pmws2223.uno.service;

import de.uniks.pmws2223.uno.model.Game;
import de.uniks.pmws2223.uno.model.Player;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.Random;

import static de.uniks.pmws2223.uno.Constants.*;
import static org.junit.Assert.assertEquals;

public class BotServiceTest extends ApplicationTest {

    @Test
    public void botService() {
        Random random = new Random();
        random.setSeed(6038369);

        RandomService randomService = new RandomService(random);

        Game game = new Game();
        Player player = new Player().setType(HUMAN);
        Player bot1 = new Player().setType(BOT);
        Player bot2 = new Player().setType(BOT);
        Player bot3 = new Player().setType(BOT);

        GameService gameService = new GameService(randomService, game);

        game.setDirection(CLOCKWISE).setDiscardPile(gameService.drawCard())
                .withPlayers(player, bot1, bot2, bot3).setCurrentPlayer(player);

        for (Player players : game.getPlayers()) {
            for (int i = 0; i <= 2; i++) {
                players.withCards(gameService.drawCard());
            }
        }

        /* Discard pile: [8, red]
           Cards of player: [skip, red], [6, green], [nine, green]
           Cards of bot1: [7, blue], [5, green], [5, blue]
           Cards of bot2: [reverse, red], [reverse, blue], [8, red]
           Cards of bot3: [0, yellow], [8, red], [8, red] */
        assertEquals(EIGHT, game.getDiscardPile().getValue());
        assertEquals(RED, game.getDiscardPile().getColor());
        assertEquals(SKIP, player.getCards().get(0).getValue());
        assertEquals(RED, player.getCards().get(0).getColor());
        assertEquals(SIX, player.getCards().get(1).getValue());
        assertEquals(GREEN, player.getCards().get(1).getColor());
        assertEquals(NINE, player.getCards().get(2).getValue());
        assertEquals(GREEN, player.getCards().get(2).getColor());
        assertEquals(SEVEN, bot1.getCards().get(0).getValue());
        assertEquals(BLUE, bot1.getCards().get(0).getColor());
        assertEquals(FIVE, bot1.getCards().get(1).getValue());
        assertEquals(GREEN, bot1.getCards().get(1).getColor());
        assertEquals(FIVE, bot1.getCards().get(2).getValue());
        assertEquals(BLUE, bot1.getCards().get(2).getColor());
        assertEquals(REVERSE, bot2.getCards().get(0).getValue());
        assertEquals(RED, bot2.getCards().get(0).getColor());
        assertEquals(REVERSE, bot2.getCards().get(1).getValue());
        assertEquals(BLUE, bot2.getCards().get(1).getColor());
        assertEquals(EIGHT, bot2.getCards().get(2).getValue());
        assertEquals(RED, bot2.getCards().get(2).getColor());
        assertEquals(ZERO, bot3.getCards().get(0).getValue());
        assertEquals(YELLOW, bot3.getCards().get(0).getColor());
        assertEquals(EIGHT, bot3.getCards().get(1).getValue());
        assertEquals(RED, bot3.getCards().get(1).getColor());
        assertEquals(EIGHT, bot3.getCards().get(2).getValue());
        assertEquals(RED, bot3.getCards().get(2).getColor());

        /* The bots are programmed in a way that they always play the first card which matches the color or value
           of the card on the discard pile or which is a wild card.
           The player in the current player. They plays [skip, red].
           Discard pile: [skip, red]
           Cards of player: [6, green], [nine, green]
           Cards of bot1: [7, blue], [5, green], [5, blue]
           Cards of bot2: [reverse, red], [reverse, blue], [8, red]
           Cards of bot3: [0, yellow], [8, red], [8, red] */
        gameService.playCard(player.getCards().get(0));
        assertEquals(2, player.getCards().size());
        assertEquals(3, bot1.getCards().size());
        assertEquals(3, bot2.getCards().size());
        assertEquals(3, bot3.getCards().size());
        assertEquals(bot2, game.getCurrentPlayer());
        assertEquals(SKIP, game.getDiscardPile().getValue());
        assertEquals(RED, game.getDiscardPile().getColor());

        /* Bot2 will play the [reverse, red].
           So bot 1 will be in line.
           Discard pile: [reverse, red]
           Cards of player: [6, green], [nine, green]
           Cards of bot1: [7, blue], [5, green], [5, blue]
           Cards of bot2: [reverse, blue], [8, red]
           Cards of bot3: [0, yellow], [8, red], [8, red] */
        sleep(2010);
        assertEquals(2, player.getCards().size());
        assertEquals(3, bot1.getCards().size());
        assertEquals(2, bot2.getCards().size());
        assertEquals(3, bot3.getCards().size());
        assertEquals(bot1, game.getCurrentPlayer());
        assertEquals(REVERSE, game.getDiscardPile().getValue());
        assertEquals(RED, game.getDiscardPile().getColor());
        assertEquals(COUNTER_CLOCKWISE, game.getDirection());

        /* Bot1 cannot play a card, so it draws one. It is a [7, blue] which also cannot be played.
           So the player will be in line.
           Discard pile: [reverse, red]
           Cards of player: [6, green], [nine, green]
           Cards of bot1: [7, blue], [5, green], [5, blue]
           Cards of bot2: [reverse, blue], [8, red]
           Cards of bot3: [0, yellow], [8, red], [8, red] */
        sleep(2010);
        assertEquals(2, player.getCards().size());
        assertEquals(4, bot1.getCards().size());
        assertEquals(2, bot2.getCards().size());
        assertEquals(3, bot3.getCards().size());
        assertEquals(player, game.getCurrentPlayer());
        assertEquals(REVERSE, game.getDiscardPile().getValue());
        assertEquals(RED, game.getDiscardPile().getColor());
        assertEquals(SEVEN, bot1.getCards().get(3).getValue());
        assertEquals(BLUE, bot1.getCards().get(3).getColor());
    }


}
