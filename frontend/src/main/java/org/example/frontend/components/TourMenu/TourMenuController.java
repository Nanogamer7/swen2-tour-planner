package org.example.frontend.components.TourMenu;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import org.example.frontend.EventHandler;
import org.example.frontend.data.TourRepository;
import org.example.frontend.data.models.Tour;

public class TourMenuController {
    private final TourMenuViewModel viewModel = new TourMenuViewModel();

    public ListView<String> lvTourNames;


    @FXML
    public void initialize() {
        // Instantiate ViewModel
        viewModel.setTours(TourRepository.fetchTours());

        // Set up ListView, including its interaction event
        lvTourNames.setItems(viewModel.getTourListViewNames());
        lvTourNames.getSelectionModel().selectedItemProperty().addListener(this::onTourSelect);
    }


    private void onTourSelect(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if (newValue == null) {
            return;
        }

        var tour = viewModel.getTourFromName(newValue);
        EventHandler.getInstance().publishTourSelectEvent(tour);
    }
}