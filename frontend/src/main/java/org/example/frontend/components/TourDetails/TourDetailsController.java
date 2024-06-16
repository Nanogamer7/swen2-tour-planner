package org.example.frontend.components.TourDetails;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.example.frontend.EventHandler;
import org.example.frontend.base.TourUpdateListener;
import org.example.frontend.data.models.Tour;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class TourDetailsController implements TourUpdateListener {
    private final TourDetailsViewModel viewModel = new TourDetailsViewModel();

    // General Tab
    public Label lblName;
    public Label lblDescription;
    public Label lblFrom;
    public Label lblTo;
    public Label lblDistance;
    public Label lblType;
    public Label lblEstimatedTime;

    // Route Tab
    public ImageView imgTourMap;
    public VBox vboxTourMap;


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

        // Route tab (TourLogs are in their own component)
        imgTourMap.setImage(new Image(viewModel.mapFilename));
    }


    @Override
    public void updateTour(Tour tour) {
        if (tour == null) return;
        viewModel.updateTour(tour);

        // Done manually as <Image> doesn't support StringProperties for url
        imgTourMap.setImage(new Image(viewModel.mapFilename));
    }

    @FXML
    public void downloadPDF() {
        try {
            UUID tourId = viewModel.getTourId();
            if (tourId == null) {
                System.out.println("No tour selected.");
                return;
            }

            String pdfUrl = "http://localhost:8080/tours/" + tourId + "/report";

            HttpURLConnection connection = (HttpURLConnection) new URL(pdfUrl).openConnection();
            connection.setRequestMethod("GET");

            InputStream inputStream = connection.getInputStream();

            FileOutputStream fileOutputStream = new FileOutputStream("TourReport_" + tourId + ".pdf");
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }

            fileOutputStream.close();
            inputStream.close();

            System.out.println("PDF downloaded successfully.");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to download PDF.");
        }
    }
}