package at.fhtw.tourplanner.services;

import at.fhtw.tourplanner.models.Tour;
import at.fhtw.tourplanner.models.TourLog;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tours")
@RequiredArgsConstructor
@Validated
public class TourController {
    private final TourService tourService;
    private final TourLogService tourLogService;

    @GetMapping("/")
    public ResponseEntity<List<Tour>> getAllTours() {
        return ResponseEntity.ok(tourService.getAllTours());
    }

    @GetMapping("/{tour_id}")
    public ResponseEntity<Tour> getTourById(@PathVariable UUID tour_id) {
        return ResponseEntity.ok(tourService.getTourById(tour_id));
    }

    @PostMapping("/")
    public ResponseEntity<Tour> createTour(@RequestBody Tour tour) {
        Tour createdTour = tourService.createTour(tour);
        return ResponseEntity.created(URI.create("/tours/" + createdTour.getId().toString())).body(createdTour);
    }

    @PutMapping("/")
    public ResponseEntity<Tour> updateTour(@RequestBody Tour tour) {
        return ResponseEntity.ok(tourService.updateTour(tour));
    }

    @DeleteMapping("/{tour_id}")
    public ResponseEntity<Void> deleteTour(@PathVariable UUID tour_id) {
        tourService.deleteTourById(tour_id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{tour_id}/logs/")
    public ResponseEntity<List<TourLog>> getTourLogs(@PathVariable UUID tour_id) {
        return ResponseEntity.ok(tourLogService.findAllOfTour(tour_id));
    }

    @PostMapping("/{tour_id}/logs/")
    public ResponseEntity<TourLog> createTourLog(@PathVariable UUID tour_id, @RequestBody TourLog tourLog) {
        return ResponseEntity.created(URI.create("yes")).body(tourLogService.create(tourLog, tour_id)); // TODO: fix URI
    }

    @GetMapping("/{tour_id}/report")
    public ResponseEntity<byte[]> getReport(@PathVariable UUID tour_id) {
        return ResponseEntity.ok(tourService.generateReport(tour_id));
    }

    @GetMapping("/{tour_id}/route")
    public ResponseEntity<String> getRoute(@PathVariable UUID tour_id) {
        return ResponseEntity.ok(tourService.getTourRouteInformationById(tour_id));
    }
}
