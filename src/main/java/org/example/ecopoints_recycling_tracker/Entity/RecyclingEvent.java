package org.example.ecopoints_recycling_tracker.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RecyclingEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    private MaterialType MatType;
    private float WeightKG;
    private LocalDateTime recyclingDate=LocalDateTime.now();
    private double EcoPoints;



    @ManyToOne
    @JoinColumn(name="household_id")
    private Household Household;


}

