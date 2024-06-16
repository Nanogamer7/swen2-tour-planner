package org.example.frontend.components.TourLogsForm;

import javafx.beans.property.*;
import lombok.extern.java.Log;
import org.example.frontend.EventHandler;
import org.example.frontend.base.LogUpdateListener;
import org.example.frontend.base.TourUpdateListener;
import org.example.frontend.data.TourRepository;
import org.example.frontend.data.models.*;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.UUID;

public class TourLogsFormViewModel implements TourUpdateListener, LogUpdateListener {
    public LongProperty timestamp = new SimpleLongProperty();

    public LongProperty distance = new SimpleLongProperty(); //meters
    public LongProperty duration = new SimpleLongProperty();
    public IntegerProperty difficulty = new SimpleIntegerProperty();
    public IntegerProperty rating = new SimpleIntegerProperty();
    public StringProperty comment = new SimpleStringProperty();
    //public ObjectProperty<TransportType> type = new SimpleObjectProperty<>();

    public UUID tourUuid = null;
    public UUID logUuid = null;

    @Override
    public void updateTour(Tour tour) {
        if (tour == null) {
            this.tourUuid = null;
            return;
        }
        this.tourUuid = tour.getUuid();
    }

    @Override
    public void updateLog(TourLog tourLog) {
        if (tourLog == null) {
            this.logUuid = null;
            this.comment.set("");
            this.difficulty.set(0);
            this.distance.set(0);
            this.duration.set(0);
            this.rating.set(0);
            return;
        }

        this.logUuid = tourLog.getUuid();
        this.comment.set(tourLog.getComment());
        this.difficulty.set(tourLog.getDifficulty());
        this.distance.set(tourLog.getDistance());
        this.duration.set(tourLog.getDuration());
        this.rating.set(tourLog.getRating());
    }

    public void submit() {
        TourLogInput input = new TourLogInput(
                this.comment.get(),
                this.difficulty.get(),
                this.distance.get(),
                this.duration.get(),
                this.rating.get()
        );

        if (this.logUuid == null) {
            TourRepository.getInstance().addTourLog(input, this.tourUuid);
        } else {
            TourRepository.getInstance().modifyLog(input, this.logUuid);
        }

        EventHandler.getInstance().updateTourLogsFormVisibility(false);
        EventHandler.getInstance().refreshTourLogsList();
        // TODO: refresh logs list
    }
}
