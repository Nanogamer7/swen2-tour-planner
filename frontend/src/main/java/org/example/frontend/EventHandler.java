package org.example.frontend;

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

    // Singleton implementation
    private static EventHandler instance;
    private EventHandler(){};

    public static EventHandler getInstance(){
        if(instance == null){
            instance = new EventHandler();
        }
        return instance;
    }

    // New tour selected event
    public void publishTourUpdateEvent(Tour selectedTour){
        for(var listener : tourUpdateListeners){
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
}
