package org.example.frontend.components.TourList;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import org.example.frontend.EventHandler;
import org.example.frontend.data.TourRepository;

public class TourListController {
    private final TourListViewModel viewModel = new TourListViewModel();

    public ListView<String> lvTourNames;


    @FXML
    public void initialize() {
        EventHandler.getInstance().registerTourListController(this);

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
        EventHandler.getInstance().publishTourUpdateEvent(tour);
    }


    public void refreshToursList(){
        viewModel.setTours(TourRepository.fetchTours());
        lvTourNames.setItems(viewModel.getTourListViewNames());
    }
}