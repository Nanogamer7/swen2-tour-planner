package org.example.frontend;

import org.example.frontend.base.TourUpdateListener;
import org.example.frontend.data.models.Tour;

import java.util.ArrayList;

/**
 * A mediator to pass UI updates between components
 */
public class EventHandler {
    private ArrayList<TourUpdateListener> tourUpdateListeners = new ArrayList<>();

    // Singleton implementation
    private static EventHandler instance;
    private EventHandler(){};

    public static EventHandler getInstance(){
        if(instance == null){
            instance = new EventHandler();
        }
        return instance;
    }

    // Event handling
    public void publishTourSelectEvent(Tour selectedTour){
        for(var listener : tourUpdateListeners){
            listener.updateTour(selectedTour);
        }
    }

    public void registerTourUpdateListener(TourUpdateListener listener){
        tourUpdateListeners.add(listener);
    }
}
