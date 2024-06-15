package at.fhtw.tourplanner;

import at.fhtw.tourplanner.models.Tour;
import at.fhtw.tourplanner.repositories.TourRepository;
import at.fhtw.tourplanner.services.TourLogService;
import at.fhtw.tourplanner.services.TourService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class TourServiceTests {

    private final TourRepository tourRepository = Mockito.mock(TourRepository.class);
    private final TourLogService tourLogService = Mockito.mock(TourLogService.class);
    private final TourService tourService = new TourService(tourRepository, tourLogService);

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
}
