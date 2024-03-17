package org.example.frontend.components.TourDetails;

import javafx.fxml.FXML;
import org.example.frontend.MainWindowController;

public class TourDetailsController {


    @FXML
    protected void onHelloButtonClick() {
        var cont = MainWindowController.getInstance();
        System.out.println(cont.getTest());
    }
}