package org.example.frontend.components.TourForm;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
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
                    setText(item.name);
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
                    setText(item.name);
                }
            }
        });
    }
}
