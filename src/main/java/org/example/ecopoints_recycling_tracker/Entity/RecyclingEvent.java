package org.example.ecopoints_recycling_tracker.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RecyclingEvent {
    @Enumerated(EnumType.STRING)
    private MaterialType MatType;
    private float WeightKG;
    private Date recyclingDate;
    private double EcoPoints;

    @ManyToOne
    private Household Household;

}

