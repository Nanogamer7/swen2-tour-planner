package at.fhtw.tourplanner.services;

import at.fhtw.tourplanner.models.TourLog;
import at.fhtw.tourplanner.repositories.TourLogRepository;
import at.fhtw.tourplanner.repositories.TourRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TourLogService {
    private final TourLogRepository tourLogRepository;
    private final TourRepository tourRepository;

    public TourLog create(TourLog tourLog, UUID tourId) {
        tourLog.setTour(tourRepository.findById(tourId).get());
        return tourLogRepository.save(tourLog);
    }

    public TourLog findById(UUID id) {
        return tourLogRepository.findById(id).orElse(null);
    }

    public List<TourLog> findAllOfTour(UUID tourId) {
        return tourLogRepository.findAllByTourId(tourId);
    }

    public TourLog update(TourLog tourLog) {
        return tourLogRepository.save(tourLog);
    }

    public void deleteById(UUID id) {
        tourLogRepository.deleteById(id);
    }
}
