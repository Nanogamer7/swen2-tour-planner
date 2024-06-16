package org.example.frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class MainWindow extends Application {
    @Getter
    private static MainWindow instance;

    @Getter
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        instance = this;

        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("MainWindowView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 600);

        primaryStage = stage;
        stage.setTitle("Tour Planner");
        stage.setScene(scene);
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.show();
    }

    public static void main(String[] args) {
        log.info("Start of setUp");
        launch();
    }
}