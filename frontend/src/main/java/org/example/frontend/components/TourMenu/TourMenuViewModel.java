package org.example.frontend.components.TourMenu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import org.example.frontend.base.OnTourSelectListener;
import org.example.frontend.data.models.Tour;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TourMenuViewModel {
    public Map<String, Integer> tours;
    public List<OnTourSelectListener> onTourSelectListeners = new ArrayList<>();

    public ObservableList<String> getTourNames(){
        return FXCollections.observableArrayList(tours.keySet());
    }
}
