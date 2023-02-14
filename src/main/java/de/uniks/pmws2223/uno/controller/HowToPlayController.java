package de.uniks.pmws2223.uno.controller;

import com.sun.tools.javac.Main;
import de.uniks.pmws2223.uno.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import java.io.IOException;

public class HowToPlayController implements Controller {

    private final App app;

    public HowToPlayController(App app) {
        this.app = app;
    }

    @Override
    public String getTitle() {
        return "UNO - How to play";
    }

    @Override
    public void init() {

    }

    @Override
    public Parent render() throws IOException {
        // load fxml
        Parent parent = FXMLLoader.load(Main.class.getResource("view/HowToPlay.fxml"));

        // lookup content
        Button backButton = (Button) parent.lookup("#backButton");

        //set button action
        backButton.setOnAction(action -> {
            app.show(new SetupController(app));
        });

        return parent;
    }

    @Override
    public void destroy() {

    }
}
