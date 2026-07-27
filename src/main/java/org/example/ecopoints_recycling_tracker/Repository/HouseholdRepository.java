package org.example.ecopoints_recycling_tracker.Repository;

import org.example.ecopoints_recycling_tracker.Entity.Household;
import org.example.ecopoints_recycling_tracker.Entity.RecyclingEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public interface HouseholdRepository extends JpaRepository<Household, Integer> {

    public HashMap<Integer, Household> CreateHouseHold();
}
