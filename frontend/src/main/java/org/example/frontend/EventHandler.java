package org.example.frontend;

import org.example.frontend.base.FormVisibilityListener;
import org.example.frontend.base.TourUpdateListener;
import org.example.frontend.components.TourList.TourListController;
import org.example.frontend.components.TourMenu.TourMenuController;
import org.example.frontend.data.models.Tour;

import java.util.ArrayList;

/**
 * A mediator to pass UI updates between components
 */
public class EventHandler {
    private ArrayList<TourUpdateListener> tourUpdateListeners = new ArrayList<>();
    private TourListController tourListController;
    private ArrayList<FormVisibilityListener> formVisibilityListeners = new ArrayList<>();

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
    public void registerTourListController(TourListController tourListController){
        this.tourListController = tourListController;
    }

    public void refreshTourList(){
        this.tourListController.refreshToursList();
    }


    public void registerFormVisibilityListener(FormVisibilityListener listener) { formVisibilityListeners.add(listener); }
    public void updateFormVisibility(boolean visible) {
        for (var listener : formVisibilityListeners) {
            listener.onFormVisible(visible);
        }
    }
}
