package org.example.ecopoints_recycling_tracker.Mappers;

import org.example.ecopoints_recycling_tracker.Dto.RecyclingEventDto;
import org.example.ecopoints_recycling_tracker.Entity.RecyclingEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RecyclingEventMapper {
  public RecyclingEvent toEntity(RecyclingEventDto dto) {
      RecyclingEvent entity = new RecyclingEvent();
      entity.setMatType(dto.getMatType());
      entity.setWeightKG(dto.getWeightKG());
      entity.setRecyclingDate(LocalDateTime.now());
      return entity;
  }
}
