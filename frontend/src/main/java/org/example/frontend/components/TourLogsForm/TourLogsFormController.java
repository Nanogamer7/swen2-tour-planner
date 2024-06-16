package org.example.frontend.components.TourLogsForm;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import javafx.util.converter.LocalTimeStringConverter;
import javafx.util.converter.NumberStringConverter;
import org.example.frontend.EventHandler;
import org.example.frontend.base.TourUpdateListener;
import org.example.frontend.data.models.TransportType;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TourLogsFormController {
    private final TourLogsFormViewModel viewModel = new TourLogsFormViewModel();

    public TextField durationField;
    public TextField distanceField;
    public TextField difficultyField;
    public TextField ratingField;
    public TextField commentField;
    public Button confirmButton;
    public Button cancelButton;

    @FXML
    public void initialize() {
        durationField.textProperty().bindBidirectional(viewModel.duration, new NumberStringConverter()  );
        distanceField.textProperty().bindBidirectional(viewModel.distance, new NumberStringConverter());
        difficultyField.textProperty().bindBidirectional(viewModel.difficulty, new NumberStringConverter());
        ratingField.textProperty().bindBidirectional(viewModel.rating, new NumberStringConverter());
        commentField.textProperty().bindBidirectional(viewModel.comment);

        confirmButton.setOnAction(event -> viewModel.submit());
        cancelButton.setOnAction(event -> EventHandler.getInstance().updateTourLogsFormVisibility(false));

        //typeDrowdown.getItems().setAll(TransportType.values());

        // This is definitely not copied from chatGPT [amogus]
        // Customize the rendering of the combobox display area


        EventHandler.getInstance().registerTourUpdateListener(viewModel);
        EventHandler.getInstance().registerLogUpdateListener(viewModel);
    }
}
