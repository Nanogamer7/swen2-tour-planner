package org.example.frontend.data;

import org.example.frontend.data.models.*;

import java.util.*;

public final class TourRepository {
    // temporary singleton until REST server to have singular source for data

    private static TourRepository INSTANCE;
    private TourRepository() {
    }

    public static TourRepository getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new TourRepository();
        }

        return INSTANCE;
    }
    // end of singleton stuff

    private final List<Tour> placeholderTours = new ArrayList<>(List.of(
            new Tour(
                UUID.fromString("ec63faae-e512-11ee-931f-b3892627d62d"),
                "Korneuburg",
                "Description for Korneuburg",
                new Coordinate(0.123, 0.234),
                new Coordinate(0.234, 0.456),
                50,
                TransportType.WALK,
                1200,
                "https://cdn.wallpapersafari.com/83/21/rNY3k2.jpg"
            ),
            new Tour(
                UUID.fromString("0e54bffe-e559-11ee-a81a-ebc3d3b4f9e3"),
                "Mittewald",
                "Description for Mittewald",
                new Coordinate(0.333, 0.333),
                new Coordinate(0.666, 0.666),
                500,
                TransportType.POGO_STICK,
                500,
                "https://cdn.wallpapersafari.com/83/21/rNY3k2.jpg"
            )
    ));

    private final Map<UUID, List<TourLog>> placeholderTourLogs = new HashMap<>(Map.of(
        UUID.fromString("ec63faae-e512-11ee-931f-b3892627d62d"), List.of(
            new TourLog("1", 1000000, "Comment 1", 1, 100, 100, 1, true),
            new TourLog("2", 2000000, "Comment 2", 2, 200, 200, 2, false),
            new TourLog("3", 3000000, "Comment 3", 3, 300, 300, 3, false)
        ),
        UUID.fromString("0e54bffe-e559-11ee-a81a-ebc3d3b4f9e3"), List.of(
            new TourLog("4", 4000000, "Comment 4", 4, 400, 400, 4, false)
        )
    ));

    // TODO: return if success
    public UUID createTour(TourInput tourInput) {
        // TODO: replace with REST call

        UUID tourUUID = UUID.randomUUID();

        placeholderTours.add(new Tour(
                tourUUID,
                tourInput.name(),
                tourInput.description(),
                tourInput.from(),
                tourInput.to(),
                new Random().nextInt() % 1000,
                tourInput.type(),
                new Random().nextInt() % 1000,
                "https://cdn.wallpapersafari.com/83/21/rNY3k2.jpg"
        ));

        placeholderTourLogs.put(tourUUID, List.of());

        return tourUUID;
    }

    public List<Tour> fetchTours() {
        // Placeholder
        return placeholderTours;
    }

    public String fetchTourImage(UUID uuid){
        for(var tour : placeholderTours){
            if(tour.uuid().equals(uuid)){
                return tour.mapFilename();
            }
        }

        throw new RuntimeException("tour with uuid not found in placeholders");
    }

    public List<TourLog> fetchTourLogs(UUID uuid){
        return placeholderTourLogs.get(uuid);
    }
}
