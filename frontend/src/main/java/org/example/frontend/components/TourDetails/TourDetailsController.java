package org.example.frontend.components.TourDetails;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.example.frontend.data.models.Coordinate;
import org.example.frontend.base.TourUpdateListener;
import org.example.frontend.data.models.TransportType;
import org.example.frontend.data.models.Tour;

import java.util.Locale;

public class TourDetailsController implements TourUpdateListener {
    public Label lblName;
    public Label lblDescription;
    public Label lblFrom;
    public Label lblTo;
    public Label lblDistance;
    public Label lblType;
    public Label lblEstimatedTime;


    @FXML
    public void initialize(){
        // Placeholder
        updateTour(new Tour(
                20,
                "Mittewald",
                "Descr",
                new Coordinate(49.323, 39.293),
                new Coordinate(23.234, 29.293),
                300,
                TransportType.BUS,
                500,
                "myMap.png"
        ));
    }


    @Override
    public void updateTour(Tour tour) {
        lblName.setText(tour.name());

        lblDescription.setText(tour.description());

        lblFrom.setText(
                String.format("%s,%s", tour.from().latitude(), tour.from().longitude())
        );

        lblTo.setText(
                String.format("%s, %s", tour.to().latitude(), tour.to().longitude())
        );

        lblDistance.setText(
                String.format("%dm", tour.distance())
        );

        lblType.setText(
                tour.type().name().toLowerCase(Locale.ROOT)
        );

        // Calculate time
        var hours = Math.floorDiv(tour.estimated_time(), 60*60);
        var minutes = Math.floorDiv(tour.estimated_time(), 60) % 60;
        var seconds = tour.estimated_time() % 60;

        lblEstimatedTime.setText(
                String.format("%02d:%02d:%02d", hours, minutes, seconds)
        );
    }
}