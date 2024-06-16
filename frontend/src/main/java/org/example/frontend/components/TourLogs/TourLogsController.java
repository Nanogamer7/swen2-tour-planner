package org.example.frontend.components.TourLogs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.example.frontend.EventHandler;
import org.example.frontend.base.TourUpdateListener;
import org.example.frontend.data.TourRepository;
import org.example.frontend.data.models.Tour;

import java.util.List;
import java.util.UUID;

public class TourLogsController implements TourUpdateListener {
    private final TourLogsViewModel viewModel = new TourLogsViewModel();
    private List<TourLogsViewModel.FormattedTourLog> allTourLogs;

    public Button btnAddTourLog;

    public TextField txtSearchField;

    public TableView tblLogs;
    public TableColumn colDate;
    public TableColumn colDuration;
    public TableColumn colDistance;
    public TableColumn colDifficulty;
    public TableColumn colRating;
    public TableColumn colComment;


    private UUID selectedTour = null;

    public VBox tourLogsForm;

    @FXML
    public void initialize(){
        EventHandler.getInstance().registerTourUpdateListener(this);
        EventHandler.getInstance().registerTourLogsUpdateListener(this);

        tourLogsForm.visibleProperty().bind(viewModel.formVisible);
        tourLogsForm.managedProperty().bind(viewModel.formVisible);

        tblLogs.visibleProperty().bind(Bindings.not(viewModel.formVisible));
        tblLogs.managedProperty().bind(Bindings.not(viewModel.formVisible));

        EventHandler.getInstance().registerTourLogsFormVisibilityListener(viewModel);

        // Bind to table
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("formattedDuration"));
        colDistance.setCellValueFactory(new PropertyValueFactory<>("distance"));
        colDifficulty.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
        colRating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        colComment.setCellValueFactory(new PropertyValueFactory<>("comment"));

        tblLogs.setItems(viewModel.formattedTourLogs);

        btnAddTourLog.setOnAction(event -> {
            EventHandler.getInstance().updateTourLogsFormVisibility(true);
        });
    }


    @Override
    public void updateTour(Tour tour) {
        if (tour == null) {
            selectedTour = null;
            return;
        }
        this.selectedTour = tour.getUuid();
        refreshTourLogsList();
    }


    public void refreshTourLogsList() {
        var tourLogs = TourRepository.getInstance().fetchTourLogs(selectedTour);

        viewModel.updateTourLogs(tourLogs);

        tblLogs.setItems(viewModel.formattedTourLogs);
    }

    public void onSearch(){
        var searchQuery = txtSearchField.getText().toLowerCase();

        ObservableList<TourLogsViewModel.FormattedTourLog> foundTourLogs;
        if(searchQuery.isEmpty()){
            foundTourLogs = viewModel.formattedTourLogs;
        } else {
            foundTourLogs = FXCollections.observableArrayList(viewModel.formattedTourLogs.stream().filter(
                    (TourLogsViewModel.FormattedTourLog tourLog) ->
                            tourLog.getDate().toLowerCase().contains(searchQuery) ||
                            tourLog.getFormattedDuration().toLowerCase().contains(searchQuery) ||
                            tourLog.getComment().toLowerCase().contains(searchQuery) ||
                            String.valueOf(tourLog.getDifficulty()).toLowerCase().contains(searchQuery) ||
                            String.valueOf(tourLog.getDistance()).toLowerCase().contains(searchQuery) ||
                            String.valueOf(tourLog.getRating()).toLowerCase().contains(searchQuery)
            ).toList());
        }

        tblLogs.setItems(foundTourLogs);
    }
}
