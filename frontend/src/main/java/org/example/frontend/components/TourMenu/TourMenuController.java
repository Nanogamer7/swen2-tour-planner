package org.example.frontend.components.TourMenu;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import org.example.frontend.EventHandler;
import org.example.frontend.components.TourForm.TourFormController;
import org.example.frontend.data.TourRepository;

import java.io.IOException;

public class TourMenuController {
    private final TourMenuViewModel viewModel = new TourMenuViewModel();

    public Button btnAddTour;
    public Button btnDeleteTour;
    public Button btnEditTour;

    public VBox tourForm;
    public VBox tourDetails;


    @FXML
    public void initialize() {
        tourForm.visibleProperty().bind(viewModel.formVisible);
        tourForm.managedProperty().bind(viewModel.formVisible);
        tourDetails.visibleProperty().bind(Bindings.not(viewModel.formVisible));
        tourDetails.managedProperty().bind(Bindings.not(viewModel.formVisible));

        EventHandler.getInstance().registerFormVisibilityListener(viewModel);
        EventHandler.getInstance().registerTourUpdateListener(viewModel);

        // Set up add tour/delete tour events here

        btnAddTour.setOnAction(event -> {
            EventHandler.getInstance().updateFormVisibility(true);
            EventHandler.getInstance().publishTourUpdateEvent(null);
        });

        btnEditTour.setOnAction(event -> {
            EventHandler.getInstance().updateFormVisibility(true);
        });

        // TODO: confirm window
        btnDeleteTour.setOnAction(event -> viewModel.deleteTour());
    }
}