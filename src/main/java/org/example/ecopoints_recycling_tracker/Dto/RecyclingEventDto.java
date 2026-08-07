package org.example.ecopoints_recycling_tracker.Dto;

import lombok.Value;
import org.example.ecopoints_recycling_tracker.Entity.MaterialType;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link org.example.ecopoints_recycling_tracker.Entity.RecyclingEvent}
 */
@Value
public class RecyclingEventDto implements Serializable {
    MaterialType MatType;
    float WeightKG;
    LocalDateTime recyclingDate;
}