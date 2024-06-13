package at.fhtw.tourplanner.repositories;

import at.fhtw.tourplanner.models.TourLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TourLogRepository extends JpaRepository<TourLog, UUID> {
    List<TourLog> findAllByTourId(UUID tourId);
}
