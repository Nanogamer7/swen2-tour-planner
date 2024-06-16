package org.example.frontend;

import org.example.frontend.base.LogUpdateListener;
import org.example.frontend.base.TourFormVisibilityListener;
import org.example.frontend.base.TourLogFormVisibilityListener;
import org.example.frontend.base.TourUpdateListener;
import org.example.frontend.components.TourList.TourListController;
import org.example.frontend.components.TourLogs.TourLogsController;
import org.example.frontend.data.models.Tour;
import org.example.frontend.data.models.TourLog;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

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

    private final ArrayList<LogUpdateListener> logUpdateListeners = new ArrayList<>();
    // TODO: more descriptive name
    public void publishLogUpdateEvent(TourLog tourLog){
        for (var listener : logUpdateListeners) {
            listener.updateLog(tourLog);
        }
    }

    public void registerLogUpdateListener(LogUpdateListener listener){
        logUpdateListeners.add(listener);
    }

    // Refresh tours list
    private TourListController tourListController;
    public void registerTourListController(TourListController tourListController){
        this.tourListController = tourListController;
    }

    public void refreshTourList(){
        this.tourListController.refreshToursList();
    }


    public void registerTourLogsUpdateListener(TourUpdateListener listener){
        tourUpdateListeners.add(listener);
    }
    // Refresh tour logs list
    private TourLogsController tourLogsController;
    public void registerTourLogsListController(TourLogsController tourLogsController){
        this.tourLogsController = tourLogsController;
    }

    public void refreshTourLogsList(){
        //Optional.ofNullable(this.tourLogsController).ifPresent(TourLogsController::refreshTourLogsList);
        this.tourLogsController.refreshTourLogsList();
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
