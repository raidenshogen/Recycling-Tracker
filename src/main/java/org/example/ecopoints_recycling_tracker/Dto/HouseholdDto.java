package org.example.ecopoints_recycling_tracker.Dto;

import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link org.example.ecopoints_recycling_tracker.Entity.Household}
 */
@Value
public class HouseholdDto implements Serializable {
    String username;
    String password;
    String email;
    String FullName;
    String country;
    String address;

}