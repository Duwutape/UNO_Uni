package de.uniks.pmws2223.uno.controller;

import de.uniks.pmws2223.uno.Main;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class CardController implements Controller {

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
        Parent parent = FXMLLoader.load(Main.class.getResource("view/Card.fxml"));

        // lookup content
        Pane cardPane = (Pane) parent.lookup("#cardPane");
        Label cardText = (Label) parent.lookup("#cardText");

        //create content
        cardText.setAlignment(Pos.CENTER);

        return parent;
    }

    @Override
    public void destroy() {

    }
}
