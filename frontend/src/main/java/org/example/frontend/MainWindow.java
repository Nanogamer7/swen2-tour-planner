package org.example.frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.IOException;

public class MainWindow extends Application {
    @Getter
    private static MainWindow instance;

    @Override
    public void start(Stage stage) throws IOException {
        instance = this;

        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("MainWindowView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 600);
        stage.setTitle("Tour Planner");
        stage.setScene(scene);
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}