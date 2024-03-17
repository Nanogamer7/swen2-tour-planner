package org.example.frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainWindowController {
    static private MainWindowController instance;
    public String test;

    public static MainWindowController getInstance() {
        return instance;
    }

    public String getTest() {
        return test;
    }

    public MainWindowController() {
        instance = this;
        test = "Hello world!";
    }

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}