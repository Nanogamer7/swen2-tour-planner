package org.example.frontend.base;

import org.example.frontend.data.models.Tour;

public interface TourUpdateListener {
    // Listens for when a new Tour is selected in the TourMenu ListeView
    // Don't forget to register it in EventHandler!
    void updateTour(Tour tour);
}
