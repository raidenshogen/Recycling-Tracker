package org.example.ecopoints_recycling_tracker.Services;

import org.example.ecopoints_recycling_tracker.Entity.Household;
import org.example.ecopoints_recycling_tracker.Entity.MaterialType;
import org.example.ecopoints_recycling_tracker.Entity.RecyclingEvent;
import org.example.ecopoints_recycling_tracker.Repository.RecyclingEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class RecyclingService {

    @Autowired
    private RecyclingEventRepository recyclingEventRepository;


    public void LogRecycling (MaterialType Mt, float Weight, Date dateRecycle){
        System.out.println("log recycling event");
        double pt = Weight * 10.00;
        RecyclingEvent e = new RecyclingEvent();
        Household household = recyclingEventRepository.save(e).getHousehold();
        if(household!=null){
            System.out.println("household already exists");
        }
        household.getRecyclingEvents().add(e);
        household.setTotalPoints(household.getTotalPoints()+pt);

    }

    public RecyclingEvent AddRecyclingEvent(RecyclingEvent RE){
        RE=recyclingEventRepository.findById(RE.getId());
        if(RE!=null){
            System.out.println("recycling event already exists");
        }
        RecyclingEvent e = new RecyclingEvent();
        e.setMatType(RE.getMatType());
        e.setHousehold(RE.getHousehold());
        e.setWeightKG(RE.getWeightKG());
        e.setRecyclingDate(RE.getRecyclingDate());
        e.getHousehold().getRecyclingEvents().add(e);
        recyclingEventRepository.save(e);
        return RE;
    }
    public void DeleteRecyclingEvent(RecyclingEvent RE){
        RE=recyclingEventRepository.findById(RE.getId());
        if(RE!=null){
            recyclingEventRepository.delete(RE);
            System.out.println("recycling event deleted");
        }else {
            System.out.println("recycling event not found");
        }

    }
    public Double CalculateTotalEcoPoints(int weight,double tenpoints){
        double EcoPoints = weight * tenpoints;
        return EcoPoints;

    }

}
