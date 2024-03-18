package org.example.frontend.data;

import org.example.frontend.data.models.Coordinate;
import org.example.frontend.data.models.Tour;
import org.example.frontend.data.models.TourLog;
import org.example.frontend.data.models.TransportType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TourRepository {
    private static List<Tour> placeholderTours = List.of(
            new Tour(
                "ec63faae-e512-11ee-931f-b3892627d62d",
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
                "0e54bffe-e559-11ee-a81a-ebc3d3b4f9e3",
                "Mittewald",
                "Description for Mittewald",
                new Coordinate(0.333, 0.333),
                new Coordinate(0.666, 0.666),
                500,
                TransportType.POGO_STICK,
                500,
                "https://cdn.wallpapersafari.com/83/21/rNY3k2.jpg"
            )
    );

    private static final Map<String, List<TourLog>> placeholderTourLogs = Map.of(
        "ec63faae-e512-11ee-931f-b3892627d62d", List.of(
            new TourLog("1", 1000000, "Comment 1", 1, 100, 100, 1, true),
            new TourLog("2", 2000000, "Comment 2", 2, 200, 200, 2, false),
            new TourLog("3", 3000000, "Comment 3", 3, 300, 300, 3, false)
        ),
        "0e54bffe-e559-11ee-a81a-ebc3d3b4f9e3", List.of(
            new TourLog("4", 4000000, "Comment 4", 4, 400, 400, 4, false)
        )
    );


    public static List<Tour> fetchTours() {
        // Placeholder
        return placeholderTours;
    }

    public static String fetchTourImage(String uuid){
        for(var tour : placeholderTours){
            if(tour.uuid().equals(uuid)){
                return tour.mapFilename();
            }
        }

        throw new RuntimeException("tour with uuid not found in placeholders");
    }

    public static List<TourLog> fetchTourLogs(String uuid){
        return placeholderTourLogs.get(uuid);
    }
}
