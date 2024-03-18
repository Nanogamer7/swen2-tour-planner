package org.example.frontend.components.TourDetails;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.example.frontend.EventHandler;
import org.example.frontend.data.models.Coordinate;
import org.example.frontend.base.TourUpdateListener;
import org.example.frontend.data.models.TransportType;
import org.example.frontend.data.models.Tour;

import java.util.Locale;

public class TourDetailsController implements TourUpdateListener {
    private final TourDetailsViewModel viewModel = new TourDetailsViewModel();

    public Label lblName;
    public Label lblDescription;
    public Label lblFrom;
    public Label lblTo;
    public Label lblDistance;
    public Label lblType;
    public Label lblEstimatedTime;


    @FXML
    public void initialize(){
        // Set label text to change on change of viewModel
        lblName.textProperty().bind(viewModel.name);
        lblDescription.textProperty().bind(viewModel.description);
        lblFrom.textProperty().bind(viewModel.from);
        lblTo.textProperty().bind(viewModel.to);
        lblDistance.textProperty().bind(viewModel.distance);
        lblType.textProperty().bind(viewModel.type);
        lblEstimatedTime.textProperty().bind(viewModel.estimatedTime);


        // Register the Controller to listen for changes in currently selected tour
        EventHandler.getInstance().registerTourUpdateListener(this);
    }


    @Override
    public void updateTour(Tour tour) {
        viewModel.updateTour(tour);
    }
}