package org.example.ecopoints_recycling_tracker.Repository;

import org.example.ecopoints_recycling_tracker.Entity.RecyclingEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface RecyclingEventRepository extends JpaRepository<RecyclingEvent, Integer> {
    RecyclingEvent findById(int id);
}
