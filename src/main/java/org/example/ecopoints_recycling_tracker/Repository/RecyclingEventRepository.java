package org.example.ecopoints_recycling_tracker.Repository;

import org.example.ecopoints_recycling_tracker.Entity.MaterialType;
import org.example.ecopoints_recycling_tracker.Entity.RecyclingEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;


@Repository
public interface RecyclingEventRepository extends JpaRepository<RecyclingEvent, Integer> {
 public void LogRecycling (MaterialType Mt, float Weight, Date dateRecycle);
}
