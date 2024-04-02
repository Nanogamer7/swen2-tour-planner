package org.example.frontend.components.TourLogs;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.example.frontend.EventHandler;
import org.example.frontend.base.TourUpdateListener;
import org.example.frontend.data.TourRepository;
import org.example.frontend.data.models.Tour;

public class TourLogsController implements TourUpdateListener {
    private final TourLogsViewModel viewModel = new TourLogsViewModel();

    public Button btnAddTourLog;

    public TableView tblLogs;
    public TableColumn colDate;
    public TableColumn colDuration;
    public TableColumn colDistance;
    public TableColumn colDifficulty;
    public TableColumn colRating;
    public TableColumn colComment;

    public VBox tourLogsForm;

    @FXML
    public void initialize(){
        EventHandler.getInstance().registerTourUpdateListener(this);

        tourLogsForm.visibleProperty().bind(viewModel.formVisible);
        tourLogsForm.managedProperty().bind(viewModel.formVisible);


        EventHandler.getInstance().registerFormVisibilityListener(viewModel);


        // Bind to table
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("formattedDuration"));
        colDistance.setCellValueFactory(new PropertyValueFactory<>("distance"));
        colDifficulty.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
        colRating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        colComment.setCellValueFactory(new PropertyValueFactory<>("comment"));

        tblLogs.setItems(viewModel.formattedTourLogs);

        btnAddTourLog.setOnAction(event -> {
            EventHandler.getInstance().updateFormVisibility(false);
            EventHandler.getInstance().publishTourUpdateEvent(null);
        });
    }


    @Override
    public void updateTour(Tour tour) {
        if (tour == null) return;

        var tourLogs = TourRepository.getInstance().fetchTourLogs(tour.uuid());

        viewModel.updateTourLogs(tourLogs);

        tblLogs.setItems(viewModel.formattedTourLogs);
    }


}
