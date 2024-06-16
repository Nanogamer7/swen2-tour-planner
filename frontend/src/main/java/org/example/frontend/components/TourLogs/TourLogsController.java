package org.example.frontend.components.TourLogs;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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

import javax.swing.text.html.Option;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TourLogsController implements TourUpdateListener {
    private final TourLogsViewModel viewModel = new TourLogsViewModel();
    private List<TourLogsViewModel.FormattedTourLog> allTourLogs;

    public Button btnAddTourLog;
    public Button btnEditTourLog;
    public Button btnRemoveTourLog;

    public TextField txtSearchField;

    public TableView<TourLogsViewModel.FormattedTourLog> tblLogs;
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
        EventHandler.getInstance().registerTourLogsListController(this);

        tourLogsForm.visibleProperty().bind(viewModel.formVisible);
        tourLogsForm.managedProperty().bind(viewModel.formVisible);

        tblLogs.visibleProperty().bind(Bindings.not(viewModel.formVisible));
        tblLogs.managedProperty().bind(Bindings.not(viewModel.formVisible));

        EventHandler.getInstance().registerTourLogsFormVisibilityListener(viewModel);
        EventHandler.getInstance().registerLogUpdateListener(viewModel);

        // Bind to table
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("formattedDuration"));
        colDistance.setCellValueFactory(new PropertyValueFactory<>("distance"));
        colDifficulty.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
        colRating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        colComment.setCellValueFactory(new PropertyValueFactory<>("comment"));

        tblLogs.setItems(viewModel.formattedTourLogs);

        tblLogs.getSelectionModel().getSelectedItems().addListener(
                (ListChangeListener.Change<?extends TourLogsViewModel.FormattedTourLog> change) -> {
                    EventHandler.getInstance().publishLogUpdateEvent(
                            tblLogs.getSelectionModel().getSelectedItem()
                    );
                }
        );

        btnAddTourLog.setOnAction(event -> {
            EventHandler.getInstance().publishLogUpdateEvent(null);
            EventHandler.getInstance().updateTourLogsFormVisibility(true);
        });

        btnEditTourLog.setOnAction(event -> {
            EventHandler.getInstance().updateTourLogsFormVisibility(true);
        });

        btnRemoveTourLog.setOnAction(event -> {
            viewModel.deleteTourLog();
            EventHandler.getInstance().refreshTourLogsList();
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
