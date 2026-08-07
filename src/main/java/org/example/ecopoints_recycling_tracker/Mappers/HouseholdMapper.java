package org.example.ecopoints_recycling_tracker.Mappers;

import org.example.ecopoints_recycling_tracker.Dto.HouseholdDto;
import org.example.ecopoints_recycling_tracker.Entity.Household;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class HouseholdMapper {

    public Household toEntity(HouseholdDto dto) {

        Household household = new Household();

        household.setUsername(dto.getUsername());
        household.setPassword(dto.getPassword());
        household.setEmail(dto.getEmail());
        household.setFullName(dto.getFullName());
        household.setCountry(dto.getCountry());
        household.setAddress(dto.getAddress());
        household.setJoinDate(LocalDate.now());
        household.setTotalPoints(0);

        return household;
    }

    public HouseholdDto toDto(Household household) {

        return new HouseholdDto(
                household.getUsername(),
                household.getPassword(),
                household.getEmail(),
                household.getFullName(),
                household.getCountry(),
                household.getAddress()
        );
    }
}