package org.example.frontend.components.TourMenu;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.frontend.EventHandler;
import org.example.frontend.base.TourFormVisibilityListener;
import org.example.frontend.base.TourUpdateListener;
import org.example.frontend.data.TourRepository;
import org.example.frontend.data.models.Tour;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TourMenuViewModel implements TourFormVisibilityListener, TourUpdateListener {
    private final Map<String, Tour> tours = new HashMap<>();  // Map<tourName, Tour>
    public BooleanProperty formVisible = new SimpleBooleanProperty(false);
    public UUID selectedTourUuid = null;

    public ObservableList<String> getTourListViewNames(){
        return FXCollections.observableArrayList(tours.keySet());
    }

    public void setTours(List<Tour> tours){
        // Transform tour list to Map of format Map<tourName, Tour>
        tours.forEach(tour -> {
            this.tours.put(tour.name(), tour);
        });
    }

    public void onTourFormVisible(boolean visible) {
        this.formVisible.set(visible);
    }
    public void updateTour(Tour tour) { this.selectedTourUuid = tour == null ? null : tour.uuid(); };

    /**
     * @param name Name from the ListView
     * @return Tour object fitting to that name
     *
     * @exception RuntimeException When no such name was found
     */
    public Tour getTourFromName(String name){
        var tour = tours.get(name);

        if(tour == null){
            throw new RuntimeException("Couldn't find tour with name '" + name + "'.");
        }

        return tour;
    }

    public void deleteTour() {
        if (selectedTourUuid == null) return;
        TourRepository.getInstance().delete(selectedTourUuid);
        EventHandler.getInstance().refreshTourList();
    }
}
