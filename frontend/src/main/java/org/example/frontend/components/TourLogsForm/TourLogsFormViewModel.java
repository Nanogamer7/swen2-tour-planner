package org.example.frontend.components.TourLogsForm;

import javafx.beans.property.*;
import org.example.frontend.EventHandler;
import org.example.frontend.base.TourUpdateListener;
import org.example.frontend.data.TourRepository;
import org.example.frontend.data.models.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class TourLogsFormViewModel implements TourUpdateListener {
    public ObjectProperty<LocalDate> date = new SimpleObjectProperty<>(LocalDate.now());

    public DoubleProperty distance = new SimpleDoubleProperty();
    public ObjectProperty<LocalTime> duration = new SimpleObjectProperty<>(LocalTime.now());
    public DoubleProperty difficulty = new SimpleDoubleProperty();
    public DoubleProperty rating = new SimpleDoubleProperty();
    public StringProperty comment = new SimpleStringProperty();
    //public ObjectProperty<TransportType> type = new SimpleObjectProperty<>();

    public UUID tourUuid = null;

    @Override
    public void updateTour(Tour tour) {

    }

    /*
    @Override
    public void updateTour(Tour tour) {
        if (tour == null) {
            tourUuid = null;

            // implicitly new tour
            description.set("");
            startLatitude.set(0);
            startLongitude.set(0);
            endLatitude.set(0);
            endLongitude.set(0);
            type.set(null);


            return;
        }

        tourUuid = tour.uuid();


        // implicitly new tour
        name.set(tour.name());
        description.set(tour.description());
        startLatitude.set(tour.from().latitude());
        startLongitude.set(tour.from().longitude());
        endLatitude.set(tour.to().latitude());
        endLongitude.set(tour.to().longitude());
        type.set(tour.type());

    }*/

    public void submit() {
        /*
        UUID tourUuid;
        if (this.tourUuid == null) {
            tourUuid = (this.tourUuid = createNewTour());
        }
        else {
            modifyTour(tourUuid = this.tourUuid);
        }*/

        TourRepository.getInstance().addTourLog(new TourLogInput(
                this.date.get(),
                this.comment.get(),
                this.difficulty.get(),
                this.distance.get(),
                this.duration.get(),
                this.rating.get()
        ));
        EventHandler.getInstance().updateTourLogsFormVisibility(false);
        // TODO: refresh logs list
    }


    /*
    public UUID createNewTour() {
        var tour = new TourInput(
                this.name.get(),
                this.description.get(),
                new Coordinate(this.startLatitude.get(), this.startLongitude.get()),
                new Coordinate(this.endLatitude.get(), this.endLongitude.get()),
                this.type.get()
        );

        return TourRepository.getInstance().createTour(tour);
    }

    public void modifyTour(UUID tourUuid) {
        var tour = new TourInput(
                this.name.get(),
                this.description.get(),
                new Coordinate(this.startLatitude.get(), this.startLongitude.get()),
                new Coordinate(this.endLatitude.get(), this.endLongitude.get()),
                this.type.get()
        );

        TourRepository.getInstance().modifyTour(tour, tourUuid);
    }
     */
}
