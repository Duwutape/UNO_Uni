package de.uniks.pmws2223.uno.controller;

import de.uniks.pmws2223.uno.App;
import de.uniks.pmws2223.uno.Main;
import de.uniks.pmws2223.uno.model.Card;
import de.uniks.pmws2223.uno.model.Game;
import de.uniks.pmws2223.uno.model.Player;
import de.uniks.pmws2223.uno.service.GameService;
import de.uniks.pmws2223.uno.service.RandomService;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static de.uniks.pmws2223.uno.Constants.*;

public class IngameController implements Controller {
    private final GameService gameService;
    private final RandomService randomService;
    private final App app;
    private final Game game;
    private Player player;
    private final List<Controller> subControllers = new ArrayList<>();
    private HBox bot0Box;
    private HBox bot1Box;
    private HBox bot2Box;
    private Label bot0Label;
    private Label bot1Label;
    private Label bot2Label;
    private PropertyChangeListener discardPileListener;
    private PropertyChangeListener playerCardsListener;
    private Button red;
    private Button blue;
    private Button yellow;
    private Button green;
    private Button drawCardButton;
    private Button skipTurnButton;
    private PropertyChangeListener hasWonListener;
    private PropertyChangeListener currentPlayerListener;
    private PropertyChangeListener botCardListener;

    public IngameController(App app, GameService gameService, RandomService randomService, Game game) {
        this.app = app;
        this.gameService = gameService;
        this.randomService = randomService;
        this.game = game;
        for (Player player : game.getPlayers()) {
            if (player.getType().equals(HUMAN)) {
                this.player = player;
            }
        }
    }

    @Override
    public String getTitle() {
        return "UNO - Ingame";
    }

    @Override
    public void init() {

    }

    @Override
    public Parent render() throws IOException {
        // load fxml
        Parent parent = FXMLLoader.load(Main.class.getResource("view/Ingame.fxml"));

        // lookup content
        bot0Box = (HBox) parent.lookup("#bot0Box");
        bot1Box = (HBox) parent.lookup("#bot1Box");
        bot2Box = (HBox) parent.lookup("#bot2Box");
        HBox playerCards = (HBox) parent.lookup("#cardBoxPlayer");
        Label playerName = (Label) parent.lookup("#namePlayerLabel");
        bot0Label = (Label) parent.lookup("#bot0Label");
        bot1Label = (Label) parent.lookup("#bot1Label");
        bot2Label = (Label) parent.lookup("#bot2Label");
        red = (Button) parent.lookup("#chooseColorRed");
        blue = (Button) parent.lookup("#chooseColorBlue");
        yellow = (Button) parent.lookup("#chooseColorYellow");
        green = (Button) parent.lookup("#chooseColorGreen");
        Pane discardColor = (Pane) parent.lookup("#discardColorPane");
        Label discardValue = (Label) parent.lookup("#discardValueLabel");
        drawCardButton = (Button) parent.lookup("#drawPileButton");
        skipTurnButton = (Button) parent.lookup("#skipTurnButton");
        Button leaveButton = (Button) parent.lookup("#leaveButton");
        Label turn = (Label) parent.lookup("#turnLabel");

        // set bot label alignment and set invisible
        bot0Label.setAlignment(Pos.CENTER);
        bot1Label.setAlignment(Pos.CENTER);
        bot2Label.setAlignment(Pos.CENTER);
        bot0Label.setVisible(false);
        bot1Label.setVisible(false);
        bot2Label.setVisible(false);

        // set bot content
        for (Player bot : game.getPlayers()) {
            if (bot.getType().equals(BOT)) {
                renderBot(bot);
            }
        }

        // set bot card listener
        for (Player bot : game.getPlayers()) {
            if (bot.getType().equals(BOT)) {
                botCardListener = event -> {
                    try {
                        renderBot(bot);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                };
                bot.listeners().addPropertyChangeListener(Player.PROPERTY_CARDS, this.botCardListener);
            }
        }

        // set player Content
        playerName.setAlignment(Pos.CENTER);
        playerName.setText(this.player.getName());
        renderPlayerCards(playerCards);

        // set listener for player cards
        playerCardsListener = event -> {
            renderPlayerCards(playerCards);
        };
        this.player.listeners().addPropertyChangeListener(Player.PROPERTY_CARDS, playerCardsListener);

        // set discard Pile
        discardValue.setAlignment(Pos.CENTER);
        discardValue.setText(game.getDiscardPile().getValue());
        createStyle(discardColor, discardValue, game.getDiscardPile());

        // set listener for discard pile
        discardPileListener = event -> {
            if (event.getNewValue() != null) {
                Card card = (Card) event.getNewValue();
                discardValue.setText(card.getValue());
                createStyle(discardColor, discardValue, card);

                if (game.getCurrentPlayer().equals(this.player) && game.getDiscardPile().getColor().equals(BLACK)) {
                    buttonDisplay(true);
                }
            }
        };
        game.listeners().addPropertyChangeListener(Game.PROPERTY_DISCARD_PILE, discardPileListener);

        // set changeColorButtons
        buttonDisplay(false);

        // set changeColorButtons action
        red.setOnAction(event -> {
            game.setDiscardPile(new Card(WILD, RED));
            buttonDisplay(false);
            gameService.nextPlayer();
            gameService.endTurn();
        });
        blue.setOnAction(event -> {
            game.setDiscardPile(new Card(WILD, BLACK));
            buttonDisplay(false);
            gameService.nextPlayer();
            gameService.endTurn();
        });
        yellow.setOnAction(event -> {
            game.setDiscardPile(new Card(WILD, YELLOW));
            buttonDisplay(false);
            gameService.nextPlayer();
            gameService.endTurn();
        });
        green.setOnAction(event -> {
            game.setDiscardPile(new Card(WILD, GREEN));
            buttonDisplay(false);
            gameService.nextPlayer();
            gameService.endTurn();
        });

        // set drawCardButton alignment
        drawCardButton.setTextAlignment(TextAlignment.CENTER);

        // set drawCardButton action
        drawCardButton.setOnAction(action -> {
            if (game.getCurrentPlayer().equals(this.player)) {
                this.player.withCards(gameService.drawCard());
                drawCardButton.setDisable(true);
                skipTurnButton.setVisible(true);
            }
        });

        // set skipTurnButton
        skipTurnButton.setVisible(false);
        skipTurnButton.setOnAction(action -> {
            skipTurnButton.setVisible(false);
            drawCardButton.setDisable(false);
            if (game.getCurrentPlayer().getType().equals(HUMAN)) {
                gameService.nextPlayer();
                gameService.endTurn();
            }
        });

        // set leave button action
        leaveButton.setOnAction(action -> {
            app.show(new SetupController(app));
        });

        // set listener for game over
        hasWonListener = event -> {
            if (event.getNewValue() != null) {
                app.show(new GameOverController(app, (Player) event.getNewValue()));
            }
        };
        game.listeners().addPropertyChangeListener(Game.PROPERTY_HAS_WON, hasWonListener);

        // set turn label text
        turn.setText(game.getCurrentPlayer().getName());

        // set turn label listener
        currentPlayerListener = event -> {
            if (event.getNewValue() != null) {
                Player player = (Player) event.getNewValue();
                turn.setText(player.getName());
            }
        };
        game.listeners().addPropertyChangeListener(Game.PROPERTY_CURRENT_PLAYER, currentPlayerListener);

        return parent;
    }

    private void renderBot(Player bot) throws IOException {
        final int cards = bot.getCards().size();

        if (bot.getName().equals(RAVEN)) {
            if (game.getPlayers().size() == 2) {

                bot1Label.setText(RAVEN);
                bot1Label.setVisible(true);
                bot1Box.getChildren().clear();

                for (int i = 1; i <= cards; i++) {
                    final CardController cardController = new CardController();
                    subControllers.add(cardController);
                    cardController.init();

                    bot1Box.getChildren().add(cardController.render());
                }
            } else {

                bot0Label.setText(RAVEN);
                bot0Label.setVisible(true);
                bot0Box.getChildren().clear();

                for (int i = 1; i <= cards; i++) {
                    final CardController cardController = new CardController();
                    subControllers.add(cardController);
                    cardController.init();

                    bot0Box.getChildren().add(cardController.render());
                }
            }
        } else if (bot.getName().equals(STORM)) {
            if (game.getPlayers().size() == 3) {

                bot2Label.setText(STORM);
                bot2Label.setVisible(true);
                bot2Box.getChildren().clear();

                for (int i = 1; i <= cards; i++) {
                    final CardController cardController = new CardController();
                    subControllers.add(cardController);
                    cardController.init();

                    bot2Box.getChildren().add(cardController.render());
                }
            } else {

                bot1Label.setText(STORM);
                bot1Label.setVisible(true);
                bot1Box.getChildren().clear();

                for (int i = 1; i <= cards; i++) {
                    final CardController cardController = new CardController();
                    subControllers.add(cardController);
                    cardController.init();

                    bot1Box.getChildren().add(cardController.render());
                }
            }
        } else {

            bot2Label.setText(CLARKE);
            bot2Label.setVisible(true);
            bot2Box.getChildren().clear();

            for (int i = 1; i <= cards; i++) {
                final CardController cardController = new CardController();
                subControllers.add(cardController);
                cardController.init();

                bot2Box.getChildren().add(cardController.render());
            }
        }
    }

    private void renderPlayerCards(HBox playerCards) {
        playerCards.getChildren().clear();

        int counter = 0;
        for (Card card : this.player.getCards()) {
            Button button = new Button();
            button.setPrefWidth(66);
            button.setPrefHeight(100);
            button.setId("card" + counter);
            counter++;
            button.setText(card.getValue());

            switch (card.getColor()) {
                case RED -> {
                    button.setTextFill(Paint.valueOf(DISPLAY_BLACK));
                    button.setStyle(BACKGROUND_RADIUS + RADIUS + "; "
                            + BORDER_RADIUS + RADIUS + "; "
                            + BACKGROUND_COLOR + DISPLAY_RED + "; "
                            + BORDER_COLOR + BLACK + "; "
                            + FONT_SIZE + SIZE_SMALL + ";"
                            + FONT_WEIGHT + BOLD);
                }
                case BLUE -> {
                    button.setTextFill(Paint.valueOf(DISPLAY_BLACK));
                    button.setStyle(BACKGROUND_RADIUS + RADIUS + "; "
                            + BORDER_RADIUS + RADIUS + "; "
                            + BACKGROUND_COLOR + DISPLAY_BLUE + ";"
                            + BORDER_COLOR + BLACK + "; "
                            + FONT_SIZE + SIZE_SMALL + ";"
                            + FONT_WEIGHT + BOLD);
                }
                case YELLOW -> {
                    button.setTextFill(Paint.valueOf(DISPLAY_BLACK));
                    button.setStyle(BACKGROUND_RADIUS + RADIUS + "; "
                            + BORDER_RADIUS + RADIUS + "; "
                            + BACKGROUND_COLOR + DISPLAY_YELLOW + ";"
                            + BORDER_COLOR + BLACK + "; "
                            + FONT_SIZE + SIZE_SMALL + ";"
                            + FONT_WEIGHT + BOLD);
                }
                case GREEN -> {
                    button.setTextFill(Paint.valueOf(DISPLAY_BLACK));
                    button.setStyle(BACKGROUND_RADIUS + RADIUS + "; "
                            + BORDER_RADIUS + RADIUS + "; "
                            + BACKGROUND_COLOR + DISPLAY_GREEN + ";"
                            + BORDER_COLOR + BLACK + "; "
                            + FONT_SIZE + SIZE_SMALL + ";"
                            + FONT_WEIGHT + BOLD);
                }
                case BLACK -> {
                    button.setTextFill(Paint.valueOf(DISPLAY_WHITE));
                    button.setStyle(BACKGROUND_RADIUS + RADIUS + "; "
                            + BORDER_RADIUS + RADIUS + "; "
                            + BACKGROUND_COLOR + DISPLAY_BLACK + ";"
                            + BORDER_COLOR + BLACK + "; "
                            + FONT_SIZE + SIZE_SMALL + ";"
                            + FONT_WEIGHT + BOLD);
                }
            }

            button.setOnAction(action -> {
                if (game.getCurrentPlayer().getType().equals(HUMAN)) {
                    gameService.playCard(card);
                    skipTurnButton.setVisible(false);
                    drawCardButton.setDisable(false);
                }
            });
            playerCards.getChildren().add(button);
        }
    }

    private void createStyle(Pane discardColor, Label discardValue, Card card) {
        switch (card.getColor()) {
            case RED -> {
                discardValue.setTextFill(Paint.valueOf(DISPLAY_BLACK));
                discardValue.setStyle(FONT_SIZE + SIZE_BIG + ";"
                        + FONT_WEIGHT + BOLD);
                discardColor.setStyle(BACKGROUND_RADIUS + RADIUS + "; "
                        + BORDER_RADIUS + RADIUS + "; "
                        + BACKGROUND_COLOR + DISPLAY_RED + "; "
                        + BORDER_COLOR + BLACK);
            }
            case BLUE -> {
                discardValue.setTextFill(Paint.valueOf(DISPLAY_BLACK));
                discardValue.setStyle(FONT_SIZE + SIZE_BIG + ";"
                        + FONT_WEIGHT + BOLD);
                discardColor.setStyle(BACKGROUND_RADIUS + RADIUS + "; "
                        + BORDER_RADIUS + RADIUS + "; "
                        + BACKGROUND_COLOR + DISPLAY_BLUE + "; "
                        + BORDER_COLOR + BLACK);
            }
            case YELLOW -> {
                discardValue.setTextFill(Paint.valueOf(DISPLAY_BLACK));
                discardValue.setStyle(FONT_SIZE + SIZE_BIG + ";"
                        + FONT_WEIGHT + BOLD);
                discardColor.setStyle(BACKGROUND_RADIUS + RADIUS + "; "
                        + BORDER_RADIUS + RADIUS + "; "
                        + BACKGROUND_COLOR + DISPLAY_YELLOW + "; "
                        + BORDER_COLOR + BLACK);
            }
            case GREEN -> {
                discardValue.setTextFill(Paint.valueOf(DISPLAY_BLACK));
                discardValue.setStyle(FONT_SIZE + SIZE_BIG + ";"
                        + FONT_WEIGHT + BOLD);
                discardColor.setStyle(BACKGROUND_RADIUS + RADIUS + "; "
                        + BORDER_RADIUS + RADIUS + "; "
                        + BACKGROUND_COLOR + DISPLAY_GREEN + "; "
                        + BORDER_COLOR + BLACK);
            }
            case BLACK -> {
                discardValue.setTextFill(Paint.valueOf(DISPLAY_WHITE));
                discardValue.setStyle(FONT_SIZE + SIZE_BIG + ";"
                        + FONT_WEIGHT + BOLD);
                discardColor.setStyle(BACKGROUND_RADIUS + RADIUS + "; "
                        + BORDER_RADIUS + RADIUS + "; "
                        + BACKGROUND_COLOR + DISPLAY_BLACK + "; "
                        + BORDER_COLOR + BLACK);
            }
        }
    }

    private void buttonDisplay(boolean bool) {
        red.setVisible(bool);
        blue.setVisible(bool);
        yellow.setVisible(bool);
        green.setVisible(bool);
    }

    @Override
    public void destroy() {
        game.listeners().removePropertyChangeListener(Game.PROPERTY_DISCARD_PILE, discardPileListener);
        this.player.listeners().removePropertyChangeListener(Player.PROPERTY_CARDS, playerCardsListener);
        game.listeners().removePropertyChangeListener(Game.PROPERTY_HAS_WON, hasWonListener);
        game.listeners().removePropertyChangeListener(Game.PROPERTY_CURRENT_PLAYER, currentPlayerListener);
        for (Player bot : game.getPlayers()){
            if (bot.getType().equals(BOT)){
                bot.listeners().removePropertyChangeListener(Player.PROPERTY_CARDS, this.botCardListener);
            }
        }
    }
}
