package org.example.frontend.components.TourLogsForm;

import javafx.beans.property.*;
import org.example.frontend.EventHandler;
import org.example.frontend.base.TourUpdateListener;
import org.example.frontend.data.TourRepository;
import org.example.frontend.data.models.*;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.UUID;

public class TourLogsFormViewModel implements TourUpdateListener {
    public LongProperty timestamp = new SimpleLongProperty();

    public LongProperty distance = new SimpleLongProperty(); //meters
    public LongProperty duration = new SimpleLongProperty();
    public IntegerProperty difficulty = new SimpleIntegerProperty();
    public IntegerProperty rating = new SimpleIntegerProperty();
    public StringProperty comment = new SimpleStringProperty();
    //public ObjectProperty<TransportType> type = new SimpleObjectProperty<>();

    public UUID tourUuid = null;

    @Override
    public void updateTour(Tour tour) {
        if (tour == null) {
            this.tourUuid = null;
            return;
        }
        this.tourUuid = tour.getUuid();
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
                Instant.now().getEpochSecond(),
                this.comment.get(),
                this.difficulty.get(),
                this.distance.get(),
                this.duration.get(),
                this.rating.get()
        ), this.tourUuid);

        EventHandler.getInstance().updateTourLogsFormVisibility(false);
        EventHandler.getInstance().refreshTourLogsList();
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
