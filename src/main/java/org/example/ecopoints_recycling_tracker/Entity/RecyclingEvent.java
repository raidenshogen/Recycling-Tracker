package org.example.ecopoints_recycling_tracker.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(exclude = "household")
public class RecyclingEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",unique = true, nullable = false)
    private int id;
    @Enumerated(EnumType.STRING)
    private MaterialType matType;
    private float WeightKG;
    private LocalDateTime recyclingDate=LocalDateTime.now();
    private double EcoPoints;

    @ManyToOne
    @JoinColumn(name="household_id")
    private Household household;


}

