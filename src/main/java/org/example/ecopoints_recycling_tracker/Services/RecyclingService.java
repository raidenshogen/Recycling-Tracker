package org.example.ecopoints_recycling_tracker.Services;

import org.example.ecopoints_recycling_tracker.Dto.RecyclingEventDto;
import org.example.ecopoints_recycling_tracker.Entity.Household;
import org.example.ecopoints_recycling_tracker.Entity.MaterialType;
import org.example.ecopoints_recycling_tracker.Entity.RecyclingEvent;
import org.example.ecopoints_recycling_tracker.Mappers.RecyclingEventMapper;
import org.example.ecopoints_recycling_tracker.Repository.HouseholdRepository;
import org.example.ecopoints_recycling_tracker.Repository.RecyclingEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class RecyclingService {


    private final RecyclingEventRepository recyclingEventRepository;
    private final RecyclingEventMapper recycEventM;
    private final HouseholdRepository householdRepository;
    public RecyclingService(RecyclingEventRepository recyclingEventRepository, RecyclingEventMapper recycEventM, HouseholdRepository householdRepository) {
        this.recyclingEventRepository = recyclingEventRepository;
        this.recycEventM = recycEventM;
        this.householdRepository = householdRepository;
    }
//    public void LogRecycling (MaterialType Mt, float Weight, Date dateRecycle){
//        System.out.println("log recycling event");
//        double pt = Weight * 10.00;
//        RecyclingEvent e = new RecyclingEvent();
//        Household household = recyclingEventRepository.save(e).getHousehold();
//        if(household!=null){
//            System.out.println("household already exists");
//        }
//        household.getRecyclingEvents().add(e);
//        household.setTotalPoints(household.getTotalPoints()+pt);
//
//    }

    public RecyclingEvent addRecyclingEvent(RecyclingEventDto dto) {

        // 1. Validate weight
        if (dto.getWeightKG() <= 0) {
            throw new IllegalArgumentException(
                    "Weight must be greater than 0"
            );
        }

        // 2. Convert DTO → Entity
        RecyclingEvent event = recycEventM.toEntity(dto);
        // 3. Find household
        Household household = householdRepository
                .findById(dto.getHouseholdId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Household with ID "
                                        + dto.getHouseholdId()
                                        + " not found"
                        )
                );

        event.setHousehold(household);
        event.getHousehold().getRecyclingEvents().add(event);

        // 4. Connect event to household
        // 5. Calculate EcoPoints
        // We can add this depending on your MaterialType logic
          event.setEcoPoints(CalculateTotalEcoPoints(dto.getWeightKG()));
        // 6. Save
        return recyclingEventRepository.save(event);
    }
    @Transactional
    public Boolean DeleteRecyclingEvent(int id){
        RecyclingEvent RE=recyclingEventRepository.findById(id).orElse(null);
        if(RE!=null){
            recyclingEventRepository.delete(RE);
            RE.getHousehold().getRecyclingEvents().remove(RE);
            System.out.println("recycling event deleted");
            return true;
        }
      return false;
    }
    public Double CalculateTotalEcoPoints(float weight){
        double tenpoints=10;
        double EcoPoints =  weight * tenpoints;
        return EcoPoints;

    }
    public float TotalCommunityWeight(){
        float totalWeight = 0;
        List<RecyclingEvent> recyclingEvents = recyclingEventRepository.findAll();
        for(RecyclingEvent e:recyclingEvents){
            totalWeight += e.getWeightKG();
        }
        return totalWeight;

    }

}
