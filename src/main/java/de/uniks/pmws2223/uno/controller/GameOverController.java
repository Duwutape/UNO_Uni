package de.uniks.pmws2223.uno.controller;

import de.uniks.pmws2223.uno.App;
import de.uniks.pmws2223.uno.Main;
import de.uniks.pmws2223.uno.model.Player;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

import static de.uniks.pmws2223.uno.Constants.HUMAN;

public class GameOverController implements Controller {

    private final App app;
    private final Player hasWon;

    public GameOverController(App app, Player hasWon) {
        this.app = app;
        this.hasWon = hasWon;
    }

    @Override
    public String getTitle() {
        return "UNO - GameOver";
    }

    @Override
    public void init() {

    }

    @Override
    public Parent render() throws IOException {
        // load fxml
        Parent parent = FXMLLoader.load(Main.class.getResource("view/GameOver.fxml"));

        // lookup content
        Label gameOver = (Label) parent.lookup("#gameOverLabel");
        Label info = (Label) parent.lookup("#infoLabel");
        Button menuButton = (Button) parent.lookup("#menuButton");

        // set label content
        if (hasWon.getType().equals(HUMAN)) {
            gameOver.setText("You won!");
            info.setVisible(false);
        } else {
            gameOver.setText("Game Over!");
            info.setText(hasWon.getName() + " won");
        }

        // set button action
        menuButton.setOnAction(action -> {
            app.show(new SetupController(app));
        });

        return parent;
    }

    @Override
    public void destroy() {

    }
}
