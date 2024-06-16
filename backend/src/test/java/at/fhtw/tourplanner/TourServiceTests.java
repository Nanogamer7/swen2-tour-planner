package at.fhtw.tourplanner;

import at.fhtw.tourplanner.data.ors.DirectionService;
import at.fhtw.tourplanner.models.Tour;
import at.fhtw.tourplanner.data.repositories.TourRepository;
import at.fhtw.tourplanner.services.TourLogService;
import at.fhtw.tourplanner.services.TourService;
import com.itextpdf.io.exceptions.IOException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class TourServiceTests {

    private final TourRepository tourRepository = Mockito.mock(TourRepository.class);
    private final DirectionService directionService = Mockito.mock(DirectionService.class);
    private final TourLogService tourLogService = Mockito.mock(TourLogService.class);
    private final TourService tourService = new TourService(tourRepository, directionService, tourLogService);

    @Test
    void testGetTourById() {
        UUID id = UUID.randomUUID();
        Tour tour = new Tour();
        tour.setId(id);

        when(tourRepository.findById(id)).thenReturn(Optional.of(tour));

        Tour foundTour = tourService.getTourById(id);
        assertNotNull(foundTour);
        assertEquals(id, foundTour.getId());
    }

    @Test
    void testCreateTour() {
        Tour tour = new Tour();
        tour.setName("Test Tour");

        when(tourRepository.save(any(Tour.class))).thenReturn(tour);

        Tour createdTour = tourService.createTour(tour);
        assertNotNull(createdTour);
        assertEquals("Test Tour", createdTour.getName());
    }

    @Test
    void testUpdateTour() {
        Tour tour = new Tour();
        tour.setName("Updated Tour");

        when(tourRepository.save(any(Tour.class))).thenReturn(tour);

        Tour updatedTour = tourService.updateTour(tour);
        assertNotNull(updatedTour);
        assertEquals("Updated Tour", updatedTour.getName());
    }

    @Test
    void testDeleteTourById() {
        UUID id = UUID.randomUUID();
        tourService.deleteTourById(id);
        Mockito.verify(tourRepository, Mockito.times(1)).deleteById(id);
    }
    @Test
    void testGetAllTours() {
        List<Tour> tours = new ArrayList<>();
        tours.add(new Tour());
        tours.add(new Tour());

        when(tourRepository.findAll()).thenReturn(tours);

        List<Tour> foundTours = tourService.getAllTours();
        assertNotNull(foundTours);
        assertEquals(2, foundTours.size());
    }

    @Test
    void testGetTourRouteInformationByIdThrowsException() throws java.io.IOException, InterruptedException {
        UUID tourId = UUID.randomUUID();

        when(tourRepository.findById(tourId)).thenReturn(Optional.of(new Tour()));
        when(directionService.getJson(any(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyBoolean()))
                .thenThrow(new RuntimeException("API error"));

        String routeInfo = tourService.getTourRouteInformationById(tourId);
        assertEquals("", routeInfo);
    }

    @Test
    void testAddTimeDistanceInfoThrowsException() throws java.io.IOException, InterruptedException {
        Tour tour = new Tour();
        tour.setStart_lat(0.0);
        tour.setStart_long(0.0);
        tour.setEnd_lat(0.0);
        tour.setEnd_long(0.0);
        tour.setType(TransportType.CAR);

        when(directionService.getTimeDistance(any(), anyDouble(), anyDouble(), anyDouble(), anyDouble()))
                .thenThrow(new IOException("API error"));

        Tour updatedTour = tourService.addTimeDistanceInfo(tour);
        assertNotNull(updatedTour);
        assertNull(updatedTour.getTime());
        assertNull(updatedTour.getDistance());
    }
}
