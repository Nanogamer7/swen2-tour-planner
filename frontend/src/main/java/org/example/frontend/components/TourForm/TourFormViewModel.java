package org.example.frontend.components.TourForm;

import javafx.beans.property.*;
import org.example.frontend.EventHandler;
import org.example.frontend.data.TourRepository;
import org.example.frontend.data.models.Coordinate;
import org.example.frontend.data.models.TourInput;
import org.example.frontend.data.models.TransportType;

public class TourFormViewModel {
    public StringProperty name = new SimpleStringProperty();
    public StringProperty description = new SimpleStringProperty();
    public DoubleProperty startLatitude = new SimpleDoubleProperty();
    public DoubleProperty startLongitude = new SimpleDoubleProperty();
    public DoubleProperty endLatitude = new SimpleDoubleProperty();
    public DoubleProperty endLongitude = new SimpleDoubleProperty();
    public ObjectProperty<TransportType> type = new SimpleObjectProperty<>();
    public void createNewTour() {
        var tour = new TourInput(
                this.name.get(),
                this.description.get(),
                new Coordinate(this.startLatitude.get(), this.startLongitude.get()),
                new Coordinate(this.endLatitude.get(), this.endLongitude.get()),
                this.type.get()
        );

        TourRepository.getInstance().createTour(tour);
        EventHandler.getInstance().refreshTourList();
    }
}
