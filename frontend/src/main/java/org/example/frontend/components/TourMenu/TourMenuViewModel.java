package org.example.frontend.components.TourMenu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.frontend.base.TourUpdateListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TourMenuViewModel {
    public Map<String, String> tours;
    public List<TourUpdateListener> onTourSelectListeners = new ArrayList<>();

    public ObservableList<String> getTourNames(){
        return FXCollections.observableArrayList(tours.keySet());
    }
}
