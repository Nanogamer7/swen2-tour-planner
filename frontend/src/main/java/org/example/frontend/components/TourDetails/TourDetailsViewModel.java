package org.example.frontend.components.TourDetails;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.stage.FileChooser;
import org.example.frontend.MainWindow;
import org.example.frontend.base.TourUpdateListener;
import org.example.frontend.data.TourRepository;
import org.example.frontend.data.models.Tour;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
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

    @Override
    public void updateTour(Tour tour) {
        name.set( tour.getName() );
        description.set( tour.getDescription() );
        from.set( String.format("%s, %s", tour.getFrom_lat(), tour.getFrom_long()) );
        to.set( String.format("%s, %s", tour.getTo_lat(), tour.getTo_long()) );
        distance.set( String.format("%dm", tour.getDistance()) );
        type.set( tour.getType().description); // enum now has member vars, not ideal yet
        // type.set( tour.type().name().toLowerCase(Locale.ROOT) );

        // Calculate time
        var hours = Math.floorDiv(tour.getEstimated_time(), 60*60);
        var minutes = Math.floorDiv(tour.getEstimated_time(), 60) % 60;
        var seconds = tour.getEstimated_time() % 60;

        estimatedTime.set(
                String.format("%02d:%02d:%02d", hours, minutes, seconds)
        );
    }

    public void saveAsJson(UUID tourId) {
        saveFile(tourId, FileType.JSON);
    }

    public void saveAsPdf(UUID tourId) {
        saveFile(tourId, FileType.PDF);
    }

    private void saveFile(UUID tourId, FileType fileType) {
        if (tourId == null) {
            // TODO: error popup
            return;
        }


        TourRepository.getInstance().getPdfReport(tourId);
        byte[] bytes = switch (fileType) {
            case PDF -> TourRepository.getInstance().getPdfReport(tourId);
            case JSON -> Objects.requireNonNull(
                    TourRepository.getInstance()
                            .getTourJSON(tourId))
                    .toString(4)
                    .getBytes();
        };

        if (bytes == null) {
            // TODO: error popup
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(fileType.getExtensionInfo(), fileType.getExtension()));
        fileChooser.setInitialFileName("tour" + fileType.getExtension().substring(1));
        fileChooser.setTitle("Save Tour");

        File file = fileChooser.showSaveDialog(MainWindow.getPrimaryStage());

        if (file == null) {
            // user clicked cancel
            return;
        }

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(bytes);
            System.out.println("File written successfully."); // TODO: Logging
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
