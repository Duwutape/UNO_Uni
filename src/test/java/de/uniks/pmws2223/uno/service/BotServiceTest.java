package de.uniks.pmws2223.uno.service;

import de.uniks.pmws2223.uno.model.Game;
import de.uniks.pmws2223.uno.model.Player;
import org.junit.Assert;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.Random;

import static de.uniks.pmws2223.uno.Constants.*;

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

        /*for (int i = 0; i <=100; i++){
            System.out.println(random.nextInt(0,14));
            System.out.println(random.nextInt(0,4));
        }*/

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
           Cards of bot3: [0, yellow], [8, red], [8, red]
           */
        Assert.assertEquals(EIGHT, game.getDiscardPile().getValue());
        Assert.assertEquals(RED, game.getDiscardPile().getColor());
        Assert.assertEquals(SKIP, player.getCards().get(0).getValue());
        Assert.assertEquals(RED, player.getCards().get(0).getColor());
        Assert.assertEquals(SIX, player.getCards().get(1).getValue());
        Assert.assertEquals(GREEN, player.getCards().get(1).getColor());
        Assert.assertEquals(NINE, player.getCards().get(2).getValue());
        Assert.assertEquals(GREEN, player.getCards().get(2).getColor());
        Assert.assertEquals(SEVEN, bot1.getCards().get(0).getValue());
        Assert.assertEquals(BLUE, bot1.getCards().get(0).getColor());
        Assert.assertEquals(FIVE, bot1.getCards().get(1).getValue());
        Assert.assertEquals(GREEN, bot1.getCards().get(1).getColor());
        Assert.assertEquals(FIVE, bot1.getCards().get(2).getValue());
        Assert.assertEquals(BLUE, bot1.getCards().get(2).getColor());
        Assert.assertEquals(REVERSE, bot2.getCards().get(0).getValue());
        Assert.assertEquals(RED, bot2.getCards().get(0).getColor());
        Assert.assertEquals(REVERSE, bot2.getCards().get(1).getValue());
        Assert.assertEquals(BLUE, bot2.getCards().get(1).getColor());
        Assert.assertEquals(EIGHT, bot2.getCards().get(2).getValue());
        Assert.assertEquals(RED, bot2.getCards().get(2).getColor());
        Assert.assertEquals(ZERO, bot3.getCards().get(0).getValue());
        Assert.assertEquals(YELLOW, bot3.getCards().get(0).getColor());
        Assert.assertEquals(EIGHT, bot3.getCards().get(1).getValue());
        Assert.assertEquals(RED, bot3.getCards().get(1).getColor());
        Assert.assertEquals(EIGHT, bot3.getCards().get(2).getValue());
        Assert.assertEquals(RED, bot3.getCards().get(2).getColor());

        /* The bots are programmed in a way that they always play the first card which matches the color or value
           of the card on the discard pile or which is a wild card.
           The player in the current player. They plays [skip, red].
           Discard pile: [skip, red]
           Cards of player: [6, green], [nine, green]
           Cards of bot1: [7, blue], [5, green], [5, blue]
           Cards of bot2: [reverse, red], [reverse, blue], [8, red]
           Cards of bot3: [0, yellow], [8, red], [8, red]*/

        gameService.playCard(player.getCards().get(0));
        Assert.assertEquals(2, player.getCards().size());
        Assert.assertEquals(3, bot1.getCards().size());
        Assert.assertEquals(3, bot2.getCards().size());
        Assert.assertEquals(3, bot3.getCards().size());
        Assert.assertEquals(bot2, game.getCurrentPlayer());
        Assert.assertEquals(SKIP, game.getDiscardPile().getValue());
        Assert.assertEquals(RED, game.getDiscardPile().getColor());

        /* Bot2 will play the [reverse, red].
           So bot 1 will be in line.
           Discard pile: [reverse, red]
           Cards of player: [6, green], [nine, green]
           Cards of bot1: [7, blue], [5, green], [5, blue]
           Cards of bot2: [reverse, blue], [8, red]
           Cards of bot3: [0, yellow], [8, red], [8, red]*/
        sleep(2010);
        Assert.assertEquals(2, player.getCards().size());
        Assert.assertEquals(3, bot1.getCards().size());
        Assert.assertEquals(2, bot2.getCards().size());
        Assert.assertEquals(3, bot3.getCards().size());
        Assert.assertEquals(bot1, game.getCurrentPlayer());
        Assert.assertEquals(REVERSE, game.getDiscardPile().getValue());
        Assert.assertEquals(RED, game.getDiscardPile().getColor());
        Assert.assertEquals(COUNTER_CLOCKWISE, game.getDirection());

        /* Bot1 cannot play a card, so it draws one. It is a [7, blue] which also cannot be played.
           So the player will be in line.
           Discard pile: [reverse, red]
           Cards of player: [6, green], [nine, green]
           Cards of bot1: [7, blue], [5, green], [5, blue]
           Cards of bot2: [reverse, blue], [8, red]
           Cards of bot3: [0, yellow], [8, red], [8, red]*/
        sleep(2010);
        Assert.assertEquals(2, player.getCards().size());
        Assert.assertEquals(4, bot1.getCards().size());
        Assert.assertEquals(2, bot2.getCards().size());
        Assert.assertEquals(3, bot3.getCards().size());
        Assert.assertEquals(player, game.getCurrentPlayer());
        Assert.assertEquals(REVERSE, game.getDiscardPile().getValue());
        Assert.assertEquals(RED, game.getDiscardPile().getColor());
        Assert.assertEquals(SEVEN, bot1.getCards().get(3).getValue());
        Assert.assertEquals(BLUE, bot1.getCards().get(3).getColor());
    }


}
