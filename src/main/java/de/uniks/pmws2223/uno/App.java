package de.uniks.pmws2223.uno;

import de.uniks.pmws2223.uno.controller.Controller;
import de.uniks.pmws2223.uno.controller.SetupController;
import de.uniks.pmws2223.uno.service.RandomService;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    private Stage stage;
    private Controller controller;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        primaryStage.setScene(new Scene(new Label("Loading...")));
        primaryStage.setTitle("Uno");

        show(new SetupController(this));
        primaryStage.show();

        primaryStage.setOnCloseRequest(e -> controller.destroy());
    }

    public void start(Stage primaryStage, RandomService randomService) throws Exception {
        this.stage = primaryStage;
        primaryStage.setScene(new Scene(new Label("Loading...")));
        primaryStage.setTitle("Uno");

        show(new SetupController(this, randomService));
        primaryStage.show();

        primaryStage.setOnCloseRequest(e -> controller.destroy());
    }

    public void show(Controller controller) {
        controller.init();
        try {
            if (controller.getTitle().equals("UNO - Setup")) {
                stage.setWidth(265);
                stage.setHeight(360);
                stage.centerOnScreen();
            } else if (controller.getTitle().equals("UNO - Ingame")) {
                stage.setWidth(990);
                stage.setHeight(840);
                stage.centerOnScreen();
            } else if (controller.getTitle().equals("UNO - GameOver")) {
                stage.setWidth(209);
                stage.setHeight(248);
                stage.centerOnScreen();
            } else if (controller.getTitle().equals("UNO - How to play")) {
                stage.setWidth(802);
                stage.setHeight(427);
                stage.centerOnScreen();
            }

            stage.setResizable(false);
            stage.getScene().setRoot(controller.render());
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }

        if (this.controller != null) {
            this.controller.destroy();
        }
        this.controller = controller;
        stage.setTitle(controller.getTitle());
    }
}