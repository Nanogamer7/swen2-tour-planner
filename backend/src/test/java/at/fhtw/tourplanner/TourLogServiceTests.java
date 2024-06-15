package at.fhtw.tourplanner;

import at.fhtw.tourplanner.models.Tour;
import at.fhtw.tourplanner.models.TourLog;
import at.fhtw.tourplanner.repositories.TourLogRepository;
import at.fhtw.tourplanner.repositories.TourRepository;
import at.fhtw.tourplanner.services.TourLogService;
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
class TourLogServiceTests {

    private final TourLogRepository tourLogRepository = Mockito.mock(TourLogRepository.class);
    private final TourRepository tourRepository = Mockito.mock(TourRepository.class);
    private final TourLogService tourLogService = new TourLogService(tourLogRepository, tourRepository);

    @Test
    void testCreateTourLog() {
        UUID tourId = UUID.randomUUID();
        TourLog tourLog = new TourLog();
        Tour tour = new Tour();
        tour.setId(tourId);

        when(tourRepository.findById(tourId)).thenReturn(Optional.of(tour));
        when(tourLogRepository.save(any(TourLog.class))).thenReturn(tourLog);

        TourLog createdTourLog = tourLogService.create(tourLog, tourId);
        assertNotNull(createdTourLog);
        assertEquals(tour, createdTourLog.getTour());
    }

    @Test
    void testFindById() {
        UUID id = UUID.randomUUID();
        TourLog tourLog = new TourLog();
        tourLog.setId(id);

        when(tourLogRepository.findById(id)).thenReturn(Optional.of(tourLog));

        TourLog foundTourLog = tourLogService.findById(id);
        assertNotNull(foundTourLog);
        assertEquals(id, foundTourLog.getId());
    }

    @Test
    void testUpdateTourLog() {
        TourLog tourLog = new TourLog();
        tourLog.setDifficulty(5);

        when(tourLogRepository.save(any(TourLog.class))).thenReturn(tourLog);

        TourLog updatedTourLog = tourLogService.update(tourLog);
        assertNotNull(updatedTourLog);
        assertEquals(5, updatedTourLog.getDifficulty());
    }

    @Test
    void testDeleteTourLogById() {
        UUID id = UUID.randomUUID();
        tourLogService.deleteById(id);
        Mockito.verify(tourLogRepository, Mockito.times(1)).deleteById(id);
    }
}

