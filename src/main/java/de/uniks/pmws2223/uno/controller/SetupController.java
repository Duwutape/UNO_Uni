package de.uniks.pmws2223.uno.controller;

import de.uniks.pmws2223.uno.App;
import de.uniks.pmws2223.uno.Main;
import de.uniks.pmws2223.uno.model.Card;
import de.uniks.pmws2223.uno.model.Game;
import de.uniks.pmws2223.uno.model.Player;
import de.uniks.pmws2223.uno.service.CardService;
import de.uniks.pmws2223.uno.service.GameService;
import de.uniks.pmws2223.uno.service.PlayerService;
import de.uniks.pmws2223.uno.service.SetupService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

import java.io.IOException;

import static de.uniks.pmws2223.uno.Constants.*;

public class SetupController implements Controller {
    private final App app;


    public SetupController(App app) {
        this.app = app;
    }

    @Override
    public String getTitle() {
        return "UNO - Setup";
    }

    @Override
    public void init() {

    }

    @Override
    public Parent render() throws IOException {
        // load fxml
        Parent parent = FXMLLoader.load(Main.class.getResource("view/Setup.fxml"));

        // lookup content
        TextField nameField = (TextField) parent.lookup("#nameField");
        Slider botSelector = (Slider) parent.lookup("#botSelector");
        Button startButton = (Button) parent.lookup("#startButton");

        // set action of button
        startButton.setOnAction(action -> {
            final PlayerService playerService = new PlayerService();
            Player player = playerService.createPlayer(nameField.getText()).setType(HUMAN);

            final SetupService setupService = new SetupService();
            final CardService cardService = new CardService();
            Game game = setupService.createGame(cardService, player, (int) botSelector.getValue());

            final GameService gameService = new GameService(game);
            app.show(new IngameController(app, gameService, cardService, game));
        });
        return parent;
    }

    @Override
    public void destroy() {

    }
}
