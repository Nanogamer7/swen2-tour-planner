package org.example.frontend.components.TourList;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import org.example.frontend.EventHandler;
import org.example.frontend.base.TourUpdateListener;
import org.example.frontend.data.TourRepository;
import org.example.frontend.data.models.Tour;

public class TourListController implements TourUpdateListener {
    private final TourListViewModel viewModel = new TourListViewModel();

    public ListView<String> lvTourNames;


    @FXML
    public void initialize() {
        EventHandler.getInstance().registerTourListController(this);

        // Instantiate ViewModel
        viewModel.setTours(TourRepository.getInstance().fetchTours());

        // Set up ListView, including its interaction event
        lvTourNames.setItems(viewModel.getTourListViewNames());
        lvTourNames.getSelectionModel().selectedItemProperty().addListener(this::onTourSelect);

        EventHandler.getInstance().registerTourUpdateListener(this);
    }


    private void onTourSelect(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if (newValue == null) {
            return;
        }

        var tour = viewModel.getTourFromName(newValue);
        EventHandler.getInstance().publishTourUpdateEvent(tour);
        EventHandler.getInstance().updateFormVisibility(false);
    }

    @Override
    public void updateTour(Tour tour) {
        // mark tour as selected -> used after creating a tour
    };

    public void refreshToursList(){
        viewModel.setTours(TourRepository.getInstance().fetchTours());
        lvTourNames.setItems(viewModel.getTourListViewNames());
    }
}