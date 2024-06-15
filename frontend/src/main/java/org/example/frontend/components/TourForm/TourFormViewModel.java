package org.example.frontend.components.TourForm;

import javafx.beans.property.*;
import org.example.frontend.EventHandler;
import org.example.frontend.base.TourUpdateListener;
import org.example.frontend.data.TourRepository;
import org.example.frontend.data.models.Tour;
import org.example.frontend.data.models.TourInput;
import org.example.frontend.data.models.TransportType;

import java.util.UUID;

public class TourFormViewModel implements TourUpdateListener {
    public StringProperty name = new SimpleStringProperty();
    public StringProperty description = new SimpleStringProperty();
    public DoubleProperty startLatitude = new SimpleDoubleProperty();
    public DoubleProperty startLongitude = new SimpleDoubleProperty();
    public DoubleProperty endLatitude = new SimpleDoubleProperty();
    public DoubleProperty endLongitude = new SimpleDoubleProperty();
    public ObjectProperty<TransportType> type = new SimpleObjectProperty<>();

    public UUID tourUuid = null;

    @Override
    public void updateTour(Tour tour) {
        if (tour == null) {
            tourUuid = null;

            // implicitly new tour
            name.set("");
            description.set("");
            startLatitude.set(0);
            startLongitude.set(0);
            endLatitude.set(0);
            endLongitude.set(0);
            type.set(null);

            return;
        }

        tourUuid = tour.getUuid();

        // implicitly new tour
        name.set(tour.getName());
        description.set(tour.getDescription());
        startLatitude.set(tour.getFrom_lat());
        startLongitude.set(tour.getFrom_long());
        endLatitude.set(tour.getFrom_lat());
        endLongitude.set(tour.getFrom_long());
        type.set(tour.getType());
    }

    public void submit() {
        UUID tourUuid;
        if (this.tourUuid == null) {
            tourUuid = (this.tourUuid = createNewTour());
        }
        else {
            modifyTour(tourUuid = this.tourUuid);
        }

        EventHandler.getInstance().updateTourFormVisibility(false);
        EventHandler.getInstance().publishTourUpdateEvent(TourRepository.getInstance().fetchTours().stream().filter(t -> t.getUuid().equals(tourUuid)).findFirst().get());
        EventHandler.getInstance().refreshTourList();
    }

    public UUID createNewTour() {
        var tour = new TourInput(
                this.name.get(),
                this.description.get(),
                this.startLatitude.get(),
                this.startLongitude.get(),
                this.endLatitude.get(),
                this.endLongitude.get(),
                this.type.get()
        );

        return TourRepository.getInstance().createTour(tour).getUuid();
    }

    public void modifyTour(UUID tourUuid) {
        var tour = new TourInput(
                this.name.get(),
                this.description.get(),
                this.startLatitude.get(),
                this.startLongitude.get(),
                this.endLatitude.get(),
                this.endLongitude.get(),
                this.type.get()
        );

        TourRepository.getInstance().modifyTour(tour, tourUuid);
    }
}
