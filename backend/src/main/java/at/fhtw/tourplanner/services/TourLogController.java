package at.fhtw.tourplanner.services;

import at.fhtw.tourplanner.models.TourLog;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/logs")
@RequiredArgsConstructor
@Validated
public class TourLogController {
    private final TourLogService tourLogService;

    @PutMapping("/{log_id}")
    public ResponseEntity<TourLog> updateTourLog(@RequestBody TourLog tourLog, @PathVariable UUID log_id) {
        tourLog.setId(log_id);
        return ResponseEntity.ok(tourLogService.update(tourLog));
    }

    @GetMapping("/{log_id}")
    public ResponseEntity<TourLog> getTourLog(@PathVariable UUID log_id) {
        return ResponseEntity.ok(tourLogService.findById(log_id));
    }

    @DeleteMapping("/{log_id}")
    public ResponseEntity<Void> deleteTourLog(@PathVariable UUID log_id) {
        tourLogService.deleteById(log_id);
        return ResponseEntity.ok().build();
    }
}
