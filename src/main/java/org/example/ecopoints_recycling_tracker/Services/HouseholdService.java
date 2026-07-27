package org.example.ecopoints_recycling_tracker.Services;

import org.example.ecopoints_recycling_tracker.Entity.Household;
import org.example.ecopoints_recycling_tracker.Repository.HouseholdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class HouseholdService {
    @Autowired
    private HouseholdRepository householdRepository;


   public HashMap<Integer, Household>CreateHouseHold(HashMap<Integer, Household> maphouseholders) {
      Household household = householdRepository.getById(maphouseholders.keySet().iterator().next());
      if (household != null) {
          System.out.println("Household already exists");

      }
      household = new Household();
      household.setAddress(maphouseholders.values().iterator().next().getAddress());
      household.setName(maphouseholders.values().iterator().next().getName());
      household.setJoinDate(maphouseholders.values().iterator().next().getJoinDate());

      householdRepository.save(household);

      return maphouseholders;
  }


}
