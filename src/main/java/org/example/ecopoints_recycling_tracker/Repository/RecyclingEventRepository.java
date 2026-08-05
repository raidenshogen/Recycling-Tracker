package org.example.ecopoints_recycling_tracker.Repository;

import org.example.ecopoints_recycling_tracker.Entity.RecyclingEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RecyclingEventRepository extends JpaRepository<RecyclingEvent, Integer> {
    RecyclingEvent findById(int id);
    public List<RecyclingEvent> findByHousehold_Id(int householdId);

}
