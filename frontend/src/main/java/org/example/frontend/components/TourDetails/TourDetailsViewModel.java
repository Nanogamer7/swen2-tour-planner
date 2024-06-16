package org.example.frontend.components.TourDetails;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.example.frontend.base.TourUpdateListener;
import org.example.frontend.data.models.Tour;

import java.util.Locale;
import java.util.UUID;

public class TourDetailsViewModel implements TourUpdateListener {
    public StringProperty name = new SimpleStringProperty();
    public StringProperty description = new SimpleStringProperty();
    public StringProperty from = new SimpleStringProperty();
    public StringProperty to = new SimpleStringProperty();
    public StringProperty distance = new SimpleStringProperty();
    public StringProperty type = new SimpleStringProperty();
    public StringProperty estimatedTime = new SimpleStringProperty();




    private UUID tourId;

    // there's no way of binding a StringProperty to an <Image> url, so we just keep it
    // in a string and have the controller deal with it
    public String mapFilename = "https://cdn.wallpapersafari.com/83/21/rNY3k2.jpg";

    public UUID getTourId() {
        return tourId;
    }

    @Override
    public void updateTour(Tour tour) {
        if (tour == null) {
            tourId = null;
            name.set("");
            description.set("");
            from.set("");
            to.set("");
            distance.set("");
            type.set("");
            estimatedTime.set("");
            mapFilename = "";
            return;
        }

        tourId= tour.uuid();
        name.set( tour.name() );
        description.set( tour.description() );
        from.set( String.format("%s,%s", tour.from().latitude(), tour.from().longitude()) );
        to.set( String.format("%s, %s", tour.to().latitude(), tour.to().longitude()) );
        distance.set( String.format("%dm", tour.distance()) );
        type.set( tour.type().name); // enum now has member vars, not ideal yet
        // type.set( tour.type().name().toLowerCase(Locale.ROOT) );

        // Calculate time
        var hours = Math.floorDiv(tour.estimated_time(), 60*60);
        var minutes = Math.floorDiv(tour.estimated_time(), 60) % 60;
        var seconds = tour.estimated_time() % 60;

        estimatedTime.set(
                String.format("%02d:%02d:%02d", hours, minutes, seconds)
        );

        mapFilename = tour.mapFilename();
    }
}
