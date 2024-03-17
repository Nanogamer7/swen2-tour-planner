package org.example.frontend.components.TourDetails;

import javafx.fxml.FXML;
import org.example.frontend.MainWindowController;
import org.example.frontend.base.OnTourSelectListener;
import org.example.frontend.data.models.Tour;

public class TourDetailsController implements OnTourSelectListener {
    @Override
    public void onTourSelect(Tour tour) {
        System.out.println("Hello world");
    }
}