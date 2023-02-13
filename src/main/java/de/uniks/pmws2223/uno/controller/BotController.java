package de.uniks.pmws2223.uno.controller;

import de.uniks.pmws2223.uno.Main;
import de.uniks.pmws2223.uno.model.Card;
import de.uniks.pmws2223.uno.model.Player;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BotController implements Controller {
    private final Player bot;
    private final List<Controller> subControllers = new ArrayList<>();
    private PropertyChangeListener botCardListener;

    public BotController(Player bot) {
        this.bot = bot;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public void init() {

    }

    @Override
    public Parent render() throws IOException {
        // load fxml
        Parent parent = FXMLLoader.load(Main.class.getResource("view/Bot.fxml"));

        // lookup content
        Label name = (Label) parent.lookup("#nameLabel");
        HBox cardBox = (HBox) parent.lookup("#cardBox");

        // set content
        name.setText(bot.getName());
        createContent(cardBox);

        // set listener for bot cards
        botCardListener = event -> {
            try {
                cardBox.getChildren().clear();
                createContent(cardBox);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        bot.listeners().addPropertyChangeListener(Player.PROPERTY_CARDS, botCardListener);

        return parent;
    }

    private void createContent(HBox cardBox) throws IOException {
        for (Card card : bot.getCards()) {
            final CardController cardController = new CardController();
            subControllers.add(cardController);
            cardController.init();
            cardBox.getChildren().add(cardController.render());
        }
    }

    @Override
    public void destroy() {
        subControllers.forEach(Controller::destroy);
        bot.listeners().removePropertyChangeListener(Player.PROPERTY_CARDS, botCardListener);
    }
}
