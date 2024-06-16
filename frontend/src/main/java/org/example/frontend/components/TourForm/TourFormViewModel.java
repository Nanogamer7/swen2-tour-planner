package org.example.frontend.components.TourForm;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import javafx.beans.property.*;
import javafx.stage.FileChooser;
import org.example.frontend.EventHandler;
import org.example.frontend.MainWindow;
import org.example.frontend.base.TourUpdateListener;
import org.example.frontend.components.TourDetails.FileType;
import org.example.frontend.data.TourRepository;
import org.example.frontend.data.models.Tour;
import org.example.frontend.data.models.TourInput;
import org.example.frontend.data.models.TransportType;

import java.io.File;
import java.io.IOException;
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
            tour = new Tour();
            tourUuid = null;
        }

        tourUuid = tour.getUuid();

        // implicitly new tour
        this.setFields(
                tour.getName(),
                tour.getDescription(),
                tour.getFrom_lat(),
                tour.getFrom_long(),
                tour.getTo_lat(),
                tour.getTo_long(),
                tour.getType()
        );
    }

    private void setFields(String name, String description, double startLatitude, double startLongitude, double endLatitude, double endLongitude, TransportType type) {
        this.name.set(name);
        this.description.set(description);
        this.startLatitude.set(startLatitude);
        this.startLongitude.set(startLongitude);
        this.endLatitude.set(endLatitude);
        this.endLongitude.set(endLongitude);
        this.type.set(type);
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

    public void swapStartEnd() {
        double tempLatitude = startLatitude.get();
        double tempLongitude = startLongitude.get();

        this.startLatitude.set(this.endLatitude.get());
        this.startLongitude.set(this.endLongitude.get());

        this.endLatitude.set(tempLatitude);
        this.endLongitude.set(tempLongitude);
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

    public void importFromFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(FileType.JSON.getExtensionInfo(), FileType.JSON.getExtension()));
        fileChooser.setTitle("Select Tour");

        File file = fileChooser.showOpenDialog(MainWindow.getPrimaryStage());

        if (file == null || !file.exists()) {
            // user clicked cancel or file does not exist
            return;
        }

        Tour tour;

        try {
            tour = JsonMapper.builder().build().readValue(file, Tour.class);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        tour.setUuid(null);
        this.updateTour(tour);
    }
}
