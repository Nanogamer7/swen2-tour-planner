package at.fhtw.tourplanner.services;

import at.fhtw.tourplanner.models.Tour;
import at.fhtw.tourplanner.repositories.TourRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TourService {
    private final TourRepository tourRepository;

    public List<Tour> getAllTours() {
        return tourRepository.findAll();
    }

    public Tour getTourById(UUID id) {
        return tourRepository.findById(id).orElse(null);
    }

    public Tour createTour(Tour tour) {
        return tourRepository.save(tour);
    }

    public Tour updateTour(Tour tour) {
        // TODO: conditionally invalidate tour logs
        return tourRepository.save(tour);
    }

    public void deleteTourById(UUID id) {
        tourRepository.deleteById(id);
    }
}
