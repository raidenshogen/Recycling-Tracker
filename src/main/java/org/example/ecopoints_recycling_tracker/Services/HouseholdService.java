package org.example.ecopoints_recycling_tracker.Services;

import org.example.ecopoints_recycling_tracker.Entity.Household;
import org.example.ecopoints_recycling_tracker.Entity.RecyclingEvent;
import org.example.ecopoints_recycling_tracker.Repository.HouseholdRepository;
import org.example.ecopoints_recycling_tracker.Repository.RecyclingEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HouseholdService {
    @Autowired
    private HouseholdRepository householdRepository;



   public Household CreateHousehold(Household household) {
       household = householdRepository.findByHouseholdId(household.getId());
      if (household != null) {
          System.out.println("Household already exists");

      }
      Household h1 = new Household();
      h1.setUsername(household.getUsername());
      h1.setPassword(household.getPassword());
      h1.setAddress(household.getAddress());
      h1.setName(household.getName());
      h1.setJoinDate(household.getJoinDate());

      householdRepository.save(household);
      Map<Integer,Household> mapHouseholders = new HashMap<>();
      mapHouseholders.put(household.getId(), household);

      return household;
  }

  public double CalculateTotalPoints(Household household) {
      List<RecyclingEvent> recyclingEventList=householdRepository.findAllRecyclingEventsByHouseholdId(household.getId());
      double points=0;
      if (recyclingEventList.isEmpty()) {
          System.out.println("No recycling events found");
      }else  {

          for (RecyclingEvent recyclingEvent : recyclingEventList) {
             points += recyclingEvent.getEcoPoints();

          }
      }
      return  points;
  }


}
