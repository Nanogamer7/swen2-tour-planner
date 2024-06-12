package at.fhtw.tourplanner.controllers;

import at.fhtw.tourplanner.models.Tour;
import at.fhtw.tourplanner.services.TourService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.DefaultUriBuilderFactory;

import javax.swing.text.html.parser.Entity;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tours")
@RequiredArgsConstructor
@Validated
public class TourController {
    private final TourService tourService;

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
}
