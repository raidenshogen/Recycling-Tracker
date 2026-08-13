package org.example.ecopoints_recycling_tracker.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link org.example.ecopoints_recycling_tracker.Entity.Household}
 */
@Value
@Data
@AllArgsConstructor
public class HouseholdDto implements Serializable {
    String username;
    String password;
    String email;
    String fullName;
    String country;
    String address;

}