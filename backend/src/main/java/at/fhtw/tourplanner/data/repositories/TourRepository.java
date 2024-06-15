package at.fhtw.tourplanner.data.repositories;

import at.fhtw.tourplanner.models.Tour;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TourRepository extends JpaRepository<Tour, UUID> {

}
