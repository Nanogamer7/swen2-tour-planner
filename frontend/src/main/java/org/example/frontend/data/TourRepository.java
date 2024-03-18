package org.example.frontend.data;

import org.example.frontend.data.models.Coordinate;
import org.example.frontend.data.models.Tour;
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
                "korneuburgMap.png"
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
                "mittewaldMap.png"
            )
    );


    public static List<Tour> fetchTours() {
        // Placeholder
        return placeholderTours;
    }
}
