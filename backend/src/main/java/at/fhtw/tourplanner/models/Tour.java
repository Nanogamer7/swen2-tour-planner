package at.fhtw.tourplanner.models;

import at.fhtw.tourplanner.TransportType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "tours")
@Data
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Tour {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String description;
    private double start_lat;
    private double start_long;
    private double end_lat;
    private double end_long;
    private String type;
    //private TransportType type;
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
