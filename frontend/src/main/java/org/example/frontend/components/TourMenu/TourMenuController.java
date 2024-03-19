package org.example.frontend.components.TourMenu;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import org.example.frontend.EventHandler;
import org.example.frontend.data.TourRepository;

public class TourMenuController {
    private final TourMenuViewModel viewModel = new TourMenuViewModel();

    public Button btnAddTour;
    public Button btnDeleteTour;


    @FXML
    public void initialize() {
        // Set up add tour/delete tour events here
    }
}