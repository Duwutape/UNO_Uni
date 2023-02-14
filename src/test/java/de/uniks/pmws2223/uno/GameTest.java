package de.uniks.pmws2223.uno;

import de.uniks.pmws2223.uno.service.RandomService;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.Random;

import static de.uniks.pmws2223.uno.Constants.*;
import static org.junit.Assert.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.hasText;

public class GameTest extends ApplicationTest {

    private App app;
    private Stage stage;
    private final static int WAIT_TIME = 2010;

    @Override
    public void start(Stage stage) throws Exception{
        this.stage = stage;
        this.app = new App();
        Random random = new Random();
        random.setSeed(6038369);
        app.start(stage, new RandomService(random));
    }

    @Test
    public void playGame() {
        // check window title
        assertEquals("UNO - Setup", stage.getTitle());

        // write Ash in textField, choose 3 bots and start game
        clickOn("#nameField");
        write("Ash");
        verifyThat("#nameField", hasText("Ash"));
        moveTo("#botSelector");
        moveBy(70,0);
        press(MouseButton.PRIMARY);
        release(MouseButton.PRIMARY);
        clickOn("#startButton");

        // check window title
        assertEquals("UNO - Ingame", stage.getTitle());

        // check discard pile, player cards and current player
        assertEquals(EIGHT, lookup("#discardValueLabel").queryLabeled().getText());
        assertEquals(SKIP, lookup("#card0").queryLabeled().getText());
        assertEquals(SIX, lookup("#card1").queryLabeled().getText());
        assertEquals(NINE, lookup("#card2").queryLabeled().getText());
        assertEquals(SEVEN, lookup("#card3").queryLabeled().getText());
        assertEquals(FIVE, lookup("#card4").queryLabeled().getText());
        assertEquals(FIVE, lookup("#card5").queryLabeled().getText());
        assertEquals(REVERSE, lookup("#card6").queryLabeled().getText());
        verifyThat("#turnLabel", hasText("Ash"));

        //player plays skip
        clickOn("#card0");

        // check discard pile, player cards and current player
        assertEquals(SKIP, lookup("#discardValueLabel").queryLabeled().getText());
        assertEquals(SIX, lookup("#card0").queryLabeled().getText());
        assertEquals(NINE, lookup("#card1").queryLabeled().getText());
        assertEquals(SEVEN, lookup("#card2").queryLabeled().getText());
        assertEquals(FIVE, lookup("#card3").queryLabeled().getText());
        assertEquals(FIVE, lookup("#card4").queryLabeled().getText());
        assertEquals(REVERSE, lookup("#card5").queryLabeled().getText());
        verifyThat("#turnLabel", hasText("bot1"));
        sleep(WAIT_TIME);

        // bot1 plays 8
        // check discard pile and current player
        assertEquals(EIGHT, lookup("#discardValueLabel").queryLabeled().getText());
        verifyThat("#turnLabel", hasText("bot2"));
        sleep(WAIT_TIME);

        // bot2 plays 2
        // check discard pile and current player
        assertEquals(TWO, lookup("#discardValueLabel").queryLabeled().getText());
        verifyThat("#turnLabel", hasText("Ash"));

        // end turn one
        // player plays reverse
        clickOn("#card5");

        // check discard pile, player cards and current player
        assertEquals(REVERSE, lookup("#discardValueLabel").queryLabeled().getText());
        assertEquals(SIX, lookup("#card0").queryLabeled().getText());
        assertEquals(NINE, lookup("#card1").queryLabeled().getText());
        assertEquals(SEVEN, lookup("#card2").queryLabeled().getText());
        assertEquals(FIVE, lookup("#card3").queryLabeled().getText());
        assertEquals(FIVE, lookup("#card4").queryLabeled().getText());
        verifyThat("#turnLabel", hasText("bot2"));
        sleep(WAIT_TIME);

        // bot2 draws card
        // check discard pile and current player
        assertEquals(REVERSE, lookup("#discardValueLabel").queryLabeled().getText());
        verifyThat("#turnLabel", hasText("bot1"));
        sleep(WAIT_TIME);

        // bot1 plays 9
        assertEquals(NINE, lookup("#discardValueLabel").queryLabeled().getText());
        verifyThat("#turnLabel", hasText("bot0"));
        sleep(WAIT_TIME);

        // bot0 plays 8
        assertEquals(EIGHT, lookup("#discardValueLabel").queryLabeled().getText());
        verifyThat("#turnLabel", hasText("Ash"));

        // end turn 2
        // player cannot play a card, so they have to draw one
        clickOn("#drawPileButton");

        // check if skip button appears
        assertTrue(lookup("#skipTurnButton").query().isVisible());

        // check discard pile, player cards and current player
        assertEquals(EIGHT, lookup("#discardValueLabel").queryLabeled().getText());
        assertEquals(SIX, lookup("#card0").queryLabeled().getText());
        assertEquals(NINE, lookup("#card1").queryLabeled().getText());
        assertEquals(SEVEN, lookup("#card2").queryLabeled().getText());
        assertEquals(FIVE, lookup("#card3").queryLabeled().getText());
        assertEquals(FIVE, lookup("#card4").queryLabeled().getText());
        assertEquals(REVERSE, lookup("#card5").queryLabeled().getText());
        verifyThat("#turnLabel", hasText("Ash"));

        // player still cannot play, so click skipTurnButton
        // check if button disappears and current player
        clickOn("#skipTurnButton");
        assertFalse(lookup("#skipTurnButton").query().isVisible());
        verifyThat("#turnLabel", hasText("bot2"));
        sleep(WAIT_TIME);

        // bot2 draws card
        // check discard pile and current player
        assertEquals(EIGHT, lookup("#discardValueLabel").queryLabeled().getText());
        verifyThat("#turnLabel", hasText("bot1"));
        sleep(WAIT_TIME);

        // bot1 plays 6
        // check discard pile and current player
        assertEquals(SIX, lookup("#discardValueLabel").queryLabeled().getText());
        verifyThat("#turnLabel", hasText("bot0"));
        sleep(WAIT_TIME);

        // bot0 plays 8
        // check discard pile and current player
        assertEquals(EIGHT, lookup("#discardValueLabel").queryLabeled().getText());
        verifyThat("#turnLabel", hasText("Ash"));
        sleep(WAIT_TIME);

        //end turn 3
        // player cannot play a card, so they have to draw one
        clickOn("#drawPileButton");

        // check discard pile, player cards and current player
        assertEquals(EIGHT, lookup("#discardValueLabel").queryLabeled().getText());
        assertEquals(SIX, lookup("#card0").queryLabeled().getText());
        assertEquals(NINE, lookup("#card1").queryLabeled().getText());
        assertEquals(SEVEN, lookup("#card2").queryLabeled().getText());
        assertEquals(FIVE, lookup("#card3").queryLabeled().getText());
        assertEquals(FIVE, lookup("#card4").queryLabeled().getText());
        assertEquals(REVERSE, lookup("#card5").queryLabeled().getText());
        assertEquals(NINE, lookup("#card6").queryLabeled().getText());
        verifyThat("#turnLabel", hasText("Ash"));

        // player still cannot play, so click skipTurnButton
        // check if button disappears and current player
        clickOn("#skipTurnButton");
        assertFalse(lookup("#skipTurnButton").query().isVisible());
        verifyThat("#turnLabel", hasText("bot2"));
        sleep(WAIT_TIME);

        // bot2 plays 2
        // check discard pile and current player
        assertEquals(TWO, lookup("#discardValueLabel").queryLabeled().getText());
        verifyThat("#turnLabel", hasText("bot1"));
        sleep(WAIT_TIME);

        // bot1 draws a card
        // check discard pile and current player
        assertEquals(TWO, lookup("#discardValueLabel").queryLabeled().getText());
        verifyThat("#turnLabel", hasText("bot0"));
        sleep(WAIT_TIME);

        // bot0 plays 8
        // check discard pile and current player
        assertEquals(EIGHT, lookup("#discardValueLabel").queryLabeled().getText());
        verifyThat("#turnLabel", hasText("Ash"));

        // end round 4
        // click on leave button and check window title
        clickOn("#leaveButton");
        assertEquals("UNO - Setup", stage.getTitle());

    }
}
