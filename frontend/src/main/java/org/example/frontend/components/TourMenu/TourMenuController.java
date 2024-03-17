package org.example.frontend.components.TourMenu;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import org.example.frontend.data.TourRepository;

public class TourMenuController {
    private final TourMenuViewModel viewModel = new TourMenuViewModel();

    // fxml references
    public ListView<String> lvTourNames;

    @FXML
    public void initialize() {
        // Fill viewModel with available tours
        viewModel.tours = TourRepository.fetchTours();

        // Instantiate ListView with tour names
        lvTourNames.setItems(viewModel.getTourNames());
        lvTourNames.getSelectionModel().selectedItemProperty().addListener(this::onTourSelected);
    }

    private void onTourSelected(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if (newValue != null) {
            System.out.println(newValue);
        }
    }
}