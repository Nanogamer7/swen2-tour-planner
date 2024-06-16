package org.example.frontend.components.TourDetails;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.example.frontend.EventHandler;
import org.example.frontend.MainWindow;
import org.example.frontend.base.TourUpdateListener;
import org.example.frontend.data.TourRepository;
import org.example.frontend.data.models.Tour;

import java.util.Optional;

public class TourDetailsController implements TourUpdateListener {
    private final TourDetailsViewModel viewModel = new TourDetailsViewModel();

    // General Tab
    public Tour selectedTour;
    public Label lblName;
    public Label lblDescription;
    public Label lblFrom;
    public Label lblTo;
    public Label lblDistance;
    public Label lblType;
    public Label lblEstimatedTime;

    public HostServices openInBrowser = MainWindow.getInstance().getHostServices();

    // Route Tab
    public ImageView imgTourMap;
    public VBox vboxTourMap;
    public Button pdfButton;


    @FXML
    public void initialize() {
        // Have this controller listen for changes to the currently selected tour
        EventHandler.getInstance().registerTourUpdateListener(this);

        // General Tab
        lblName.textProperty().bind(viewModel.name);
        lblDescription.textProperty().bind(viewModel.description);
        lblFrom.textProperty().bind(viewModel.from);
        lblTo.textProperty().bind(viewModel.to);
        lblDistance.textProperty().bind(viewModel.distance);
        lblType.textProperty().bind(viewModel.type);
        lblEstimatedTime.textProperty().bind(viewModel.estimatedTime);

        pdfButton.setOnAction(event -> viewModel.saveAsPdf(Optional.ofNullable(this.selectedTour).map(Tour::getUuid).orElse(null)));

        // Route tab (TourLogs are in their own component)
        imgTourMap.setImage(new Image(String.valueOf(TourDetailsController.class.getResource("/org/example/frontend/placeholder_map.png"))));
    }


    @Override
    public void updateTour(Tour tour) {
        if (tour == null) return;

        selectedTour = tour;

        // Update all the strings that describe the currently selected tour
        viewModel.updateTour(tour);
    }

    public void openTourMap(){
        try {
            TourRepository.getInstance().downloadTourDirectionGeoJson(selectedTour.getUuid());
        } catch(Exception e){
            throw new RuntimeException(e);
        }

        openInBrowser.showDocument(String.valueOf(TourDetailsController.class.getResource("/org/example/frontend/map.html")));
    }
}