package com.rshakirova.computationalpracticum;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import java.io.IOException;

public class SolutionApplication extends Application {
    private static final FXMLLoader fxmlLoader = new FXMLLoader(SolutionApplication.class.getResource("first-page.fxml"));
    public static boolean isGteWindow;

    @Override
    public void start(Stage stage) throws IOException {
        isGteWindow = false;
        Scene SCENE_ONE = new Scene(fxmlLoader.load());
        stage.setTitle("Calculations!");
        stage.setScene(SCENE_ONE);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}