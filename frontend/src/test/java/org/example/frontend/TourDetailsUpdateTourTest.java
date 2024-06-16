package org.example.frontend;
import javafx.beans.property.StringProperty;
import org.example.frontend.components.TourDetails.TourDetailsViewModel;

import org.example.frontend.data.models.Tour;
import org.example.frontend.data.models.TransportType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javafx.beans.property.SimpleStringProperty;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TourDetailsUpdateTourTest {

    @Mock
    private Tour tour;

    @Test
    public void testUpdateTour_updatesAllProperties() {
        TourDetailsViewModel viewModel = new TourDetailsViewModel();

        when(tour.getName()).thenReturn("Test Tour");
        when(tour.getDescription()).thenReturn("This is a test tour description.");
        when(tour.getFrom_lat()).thenReturn(48.20849);
        when(tour.getFrom_long()).thenReturn(16.37208);
        when(tour.getTo_lat()).thenReturn(48.20921);
        when(tour.getTo_long()).thenReturn(16.37074);
        when(tour.getDistance()).thenReturn(1000L);
        when(tour.getType()).thenReturn(TransportType.FOOT_WALK); // Assuming Tour.Type is an enum
        when(tour.getEstimated_time()).thenReturn(7200L); // 2 hours

        viewModel.updateTour(tour);

        assertEquals("Test Tour", viewModel.name.get());
        assertEquals("This is a test tour description.", viewModel.description.get());
        assertEquals("48.20849, 16.37208", viewModel.from.get());
        assertEquals("48.20921, 16.37074", viewModel.to.get());
        assertEquals("1000m", viewModel.distance.get());
        assertEquals(TransportType.FOOT_WALK.description, viewModel.type.get()); // Assuming description is a member variable
        assertEquals("02:00:00", viewModel.estimatedTime.get());
    }
}