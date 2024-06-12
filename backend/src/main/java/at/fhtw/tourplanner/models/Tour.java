package at.fhtw.tourplanner.models;

import at.fhtw.tourplanner.Point;
import at.fhtw.tourplanner.PointConverter;
import at.fhtw.tourplanner.TransportType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "tours")
@Data
public class Tour {
    @Id
    private UUID id;
    private String name;
    private String description;
    @Convert(converter = PointConverter.class)
    private Point startPoint;
    @Convert(converter = PointConverter.class)
    private Point endPoint;
    private TransportType type;
    @Transient
    private int distance;
    @Transient
    public int getDistance() {
        // TODO: request from route service
        return 0;
    }
    @Transient
    private int time;
    @Transient
    public int getTime() {
        // TODO: request from route service
        return 0;
    }
}
