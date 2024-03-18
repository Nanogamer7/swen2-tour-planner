package org.example.frontend.components.TourMenu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.frontend.data.models.Tour;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TourMenuViewModel {
    private final Map<String, Tour> tours = new HashMap<>();  // Map<tourName, Tour>


    public ObservableList<String> getTourListViewNames(){
        return FXCollections.observableArrayList(tours.keySet());
    }

    public void setTours(List<Tour> tours){
        // Transform tour list to Map of format Map<tourName, Tour>
        tours.forEach(tour -> {
            this.tours.put(tour.name(), tour);
        });
    }

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
}
