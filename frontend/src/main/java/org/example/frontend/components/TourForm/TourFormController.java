package org.example.frontend.components.TourForm;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;
import org.example.frontend.EventHandler;
import org.example.frontend.base.TourUpdateListener;
import org.example.frontend.data.models.TransportType;

public class TourFormController {
    private final TourFormViewModel viewModel = new TourFormViewModel();

    public TextField nameField;
    public TextField descrField;
    public TextField startLatitudeField;
    public TextField startLongitudeField;
    public TextField endLatitudeField;
    public TextField endLongitudeField;
    public ComboBox<TransportType> typeDrowdown;
    public Button confirmButton;
    public Button cancelButton;

    @FXML
    public void initialize() {
        nameField.textProperty().bindBidirectional(viewModel.name);
        descrField.textProperty().bindBidirectional(viewModel.description);
        startLatitudeField.textProperty().bindBidirectional(viewModel.startLatitude, new NumberStringConverter());
        startLongitudeField.textProperty().bindBidirectional(viewModel.startLongitude, new NumberStringConverter());
        endLatitudeField.textProperty().bindBidirectional(viewModel.endLatitude, new NumberStringConverter());
        endLongitudeField.textProperty().bindBidirectional(viewModel.endLongitude, new NumberStringConverter());
        typeDrowdown.valueProperty().bindBidirectional(viewModel.type);

        confirmButton.setOnAction(event -> viewModel.submit());
        cancelButton.setOnAction(event -> EventHandler.getInstance().updateTourFormVisibility(false));

        typeDrowdown.getItems().setAll(TransportType.values());

        // This is definitely not copied from chatGPT [amogus]
        // Customize the rendering of the combobox display area
        typeDrowdown.setButtonCell(new ListCell<TransportType>() {
            @Override
            protected void updateItem(TransportType item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.description);
                }
            }
        });

        // Customize the rendering of the combobox dropdown list
        typeDrowdown.setCellFactory(lv -> new ListCell<TransportType>() {
            @Override
            protected void updateItem(TransportType item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.description);
                }
            }
        });

        EventHandler.getInstance().registerTourUpdateListener(viewModel);
    }
}
