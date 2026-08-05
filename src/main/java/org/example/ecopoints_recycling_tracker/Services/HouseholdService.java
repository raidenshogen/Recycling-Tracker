package org.example.ecopoints_recycling_tracker.Services;

import org.example.ecopoints_recycling_tracker.Entity.Household;
import org.example.ecopoints_recycling_tracker.Entity.RecyclingEvent;
import org.example.ecopoints_recycling_tracker.Repository.HouseholdRepository;
import org.example.ecopoints_recycling_tracker.Repository.RecyclingEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HouseholdService {
    @Autowired
    private HouseholdRepository householdRepository;
  @Autowired
    private RecyclingEventRepository recyclingEventRepository;


   public Household CreateHousehold(Household household) {
       household = householdRepository.findById(household.getId());
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
      List<RecyclingEvent> recyclingEventList=recyclingEventRepository.findByHousehold_Id(household.getId());
      double points=0;
      if (recyclingEventList.isEmpty()) {
          System.out.println("No recycling events found");
      }else  {

          for (RecyclingEvent recyclingEvent : recyclingEventList) {
             points += recyclingEvent.getEcoPoints();

          }
      }
      household.setTotalPoints(points);
      return  points;
  }

  public void SavelogsForHouseholds() throws IOException {
      FileWriter fw=new FileWriter("logs");

      BufferedWriter bw=null;

      try{
       bw=new BufferedWriter(fw);
       bw.write("List of Households With there Recycling event:");
       bw.newLine();
       int count=1;
       for (Household household1 : householdRepository.findAll()) {
           bw.write("Household"+ count +":");
           bw.newLine();
           bw.write(household1.getName());
           bw.newLine();
           bw.write(household1.getAddress());
           bw.newLine();
           bw.write(household1.getJoinDate().toString());
           bw.newLine();
           bw.write((int) household1.getTotalPoints());
           bw.newLine();

           int count2=1;
           for(RecyclingEvent recyclingEvent : household1.getRecyclingEvents()) {
               bw.write("Recycling Event"+ count2 +":");
               bw.newLine();
               bw.write("Material: "+recyclingEvent.getMatType());
               bw.newLine();
               bw.write("Weight in KG: "+recyclingEvent.getWeightKG());
               bw.newLine();
               bw.write("Recycling Date: "+recyclingEvent.getRecyclingDate().toString());
               bw.newLine();
               bw.write("Eco points :"+recyclingEvent.getEcoPoints());
               count2++;
               bw.close();

           }
          count++;
       }

      }catch(IOException e){
          e.printStackTrace();
      }

  }
 public List<RecyclingEvent> findAllRecyclingEventsByHouseholdId(int id) {
       List<RecyclingEvent> lre=recyclingEventRepository.findByHousehold_Id(id);
       return lre;
 }
 public float CalculateTotalWeights(Household household) {

       List<RecyclingEvent>re=findAllRecyclingEventsByHouseholdId(household.getId());
       float weigts=0;
       for (RecyclingEvent recyclingEvent : re) {

           weigts+=recyclingEvent.getWeightKG();
       }
       return weigts;

 }

 public Household HighestTotalPoints() {
       List<Household>householdList=householdRepository.findAll();

       Map<Household,Double > mapHouseholds = new HashMap<>();

       for (Household household : householdList) {
           mapHouseholds.put(household,household.getTotalPoints() );
       }

       Household h1 = mapHouseholds.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();

       return h1;
 }


}
