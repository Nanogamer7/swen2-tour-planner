package at.fhtw.tourplanner.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.hateoas.server.core.Relation;

import java.util.UUID;

@Entity
@Table(name = "tours")
@Data
public class Tour {
    @Id
    @Column
    private UUID id;
    @Column
    private String name;
    @Column
    private String description;
}
