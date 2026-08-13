package org.example.ecopoints_recycling_tracker.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import org.example.ecopoints_recycling_tracker.Entity.MaterialType;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link org.example.ecopoints_recycling_tracker.Entity.RecyclingEvent}
 */
@Value
@Data
@AllArgsConstructor
public class RecyclingEventDto implements Serializable {
    private MaterialType matType;
    private float WeightKG;
    private int householdId;

}