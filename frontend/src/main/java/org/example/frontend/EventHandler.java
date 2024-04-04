package org.example.frontend;

import org.example.frontend.base.TourFormVisibilityListener;
import org.example.frontend.base.TourLogFormVisibilityListener;
import org.example.frontend.base.TourUpdateListener;
import org.example.frontend.components.TourList.TourListController;
import org.example.frontend.data.models.Tour;

import java.util.ArrayList;

/**
 * A mediator to pass UI updates between components
 */
public class EventHandler {

    // Singleton implementation
    private static EventHandler instance;
    private EventHandler(){};

    public static EventHandler getInstance(){
        if (instance == null) {
            instance = new EventHandler();
        }
        return instance;
    }

    // New tour selected event

    private final ArrayList<TourUpdateListener> tourUpdateListeners = new ArrayList<>();
    // TODO: more descriptive name
    public void publishTourUpdateEvent(Tour selectedTour){
        for (var listener : tourUpdateListeners) {
            listener.updateTour(selectedTour);
        }
    }

    public void registerTourUpdateListener(TourUpdateListener listener){
        tourUpdateListeners.add(listener);
    }

    // Refresh tours list
    private TourListController tourListController;
    public void registerTourListController(TourListController tourListController){
        this.tourListController = tourListController;
    }

    public void refreshTourList(){
        this.tourListController.refreshToursList();
    }



    private final ArrayList<TourFormVisibilityListener> tourFormVisibilityListeners = new ArrayList<>();
    public void registerTourFormVisibilityListener(TourFormVisibilityListener listener) { tourFormVisibilityListeners.add(listener); }
    public void updateTourFormVisibility(boolean visible) {
        for (var listener : tourFormVisibilityListeners) {
            listener.onTourFormVisible(visible);
        }
    }

    private final ArrayList<TourLogFormVisibilityListener> tourLogFormVisibilityListeners = new ArrayList<>();
    public void registerTourLogsFormVisibilityListener(TourLogFormVisibilityListener listener) { tourLogFormVisibilityListeners.add(listener); }
    public void updateTourLogsFormVisibility(boolean visible) {
        for (var listener : tourLogFormVisibilityListeners) {
            listener.onLogsFormVisible(visible);
        }
    }
}
