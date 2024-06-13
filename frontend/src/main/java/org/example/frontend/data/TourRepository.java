package org.example.frontend.data;

import org.example.frontend.data.models.*;

import java.io.*;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.net.http.HttpClient;

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
                new Coordinate(8.05, 48.125),
                new Coordinate(8.1, 48.1),
                50,
                TransportType.WALK,
                100
            ),
            new Tour(
                UUID.fromString("0e54bffe-e559-11ee-a81a-ebc3d3b4f9e3"),
                "Mittewald",
                "Description for Mittewald",
                new Coordinate(8.2, 49.1),
                new Coordinate(8.25, 49.125),
                500,
                TransportType.POGO_STICK,
                500
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
                new Random().nextInt() % 1000
        ));

        placeholderTourLogs.put(tourUUID, List.of());

        return tourUUID;
    }

    // TODO: return if success
    public void modifyTour(TourInput tourInput, UUID tourUUID) {
        // TODO: replace with REST call

        placeholderTours.replaceAll(tour -> tour.uuid() == tourUUID ? new Tour(
                tourUUID,
                tourInput.name(),
                tourInput.description(),
                tourInput.from(),
                tourInput.to(),
                new Random().nextInt() % 1000,
                tourInput.type(),
                new Random().nextInt() % 1000
        ) : tour);

        placeholderTourLogs.get(tourUUID).forEach(tourLog -> new TourLog(
                tourLog.getUuid(),
                tourLog.getTimestamp(),
                tourLog.getComment(),
                tourLog.getDifficulty(),
                tourLog.getDistance(),
                tourLog.getDuration(),
                tourLog.getRating(),
                true
        ));
    }

    // TODO: return if success
    public void delete(UUID tourUUID) {
        // TODO: replace with REST call

        placeholderTours.removeIf(tour -> tour.uuid() == tourUUID);

        placeholderTourLogs.remove(tourUUID);
    }

    public List<Tour> fetchTours() {
        // Placeholder
        return placeholderTours;
    }

    /**
     * Downloads the GeoJSON for the tour route. This geojson is saved to a directions.js file in /resources/,
     * which is ultimately used by our map.html to visually display our route.
     */
    public void downloadTourDirectionGeoJson(UUID uuid) throws IOException {
        Tour selectedTour = null;
        for(var tour : placeholderTours) {
            if (tour.uuid().equals(uuid)) {
                selectedTour = tour;
            }
        }

        try {
            Path resourcesDirectory = Paths.get("src", "main", "resources", "org", "example", "frontend");
            Path outputPath = resourcesDirectory.resolve("directions.js");

            System.out.println("Downloading directions geojson from %s\nSaving it to %s".formatted(
                    selectedTour.getOpenRouteServiceURL(),
                    outputPath.toAbsolutePath().toString())
            );

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(selectedTour.getOpenRouteServiceURL()))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Write response to directions.js
            Files.writeString(outputPath, "var directions = " + response.body());
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    public List<TourLog> fetchTourLogs(UUID uuid){
        return placeholderTourLogs.get(uuid);
    }
}
