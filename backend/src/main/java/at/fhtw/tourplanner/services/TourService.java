package at.fhtw.tourplanner.services;

import at.fhtw.tourplanner.data.ors.DirectionService;
import at.fhtw.tourplanner.models.Tour;
import at.fhtw.tourplanner.data.repositories.TourRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TourService {
    private final TourRepository tourRepository;
    private final DirectionService directionService;

    public List<Tour> getAllTours() {
        return tourRepository.findAll().stream().peek(
                this::addTimeDistanceInfo
        ).collect(Collectors.toList());
    }

    public Tour getTourById(UUID id) {
        return tourRepository.findById(id).orElse(null);
    }

    public Tour createTour(Tour tour) {
        return addTimeDistanceInfo(tourRepository.save(tour));
    }

    private Tour addTimeDistanceInfo(Tour tour) {
        try {
            Pair<Integer, Integer> timeDist = directionService.getTimeDistance(
                    tour.getType(),
                    tour.getStart_lat(),
                    tour.getStart_long(),
                    tour.getEnd_lat(),
                    tour.getEnd_long()
            );
            tour.setTime(timeDist.getFirst());
            tour.setDistance(timeDist.getSecond());
        } catch (Exception e) {
            e.printStackTrace(); // TODO: logging here
            // do nothing
        }
        return tour;
    }

    public Tour updateTour(Tour tour) {
        // TODO: conditionally invalidate tour logs
        return tourRepository.save(tour);
    }

    public void deleteTourById(UUID id) {
        tourRepository.deleteById(id);
    }

    public byte[] generateReport(UUID id) {
        Tour tour = getTourById(id);
        // pdf file here
        return new byte[0];
    }

    public String getTourRouteInformationById(UUID id) {
        Tour tour = getTourById(id);
        try {
            return directionService.getRouteInformation(tour.getType(), tour.getStart_lat(), tour.getStart_long(), tour.getEnd_lat(), tour.getEnd_long()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            // http 500 do nothing
        }

        return "";
    }
}
