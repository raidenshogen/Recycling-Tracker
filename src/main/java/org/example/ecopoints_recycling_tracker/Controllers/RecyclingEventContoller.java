package org.example.ecopoints_recycling_tracker.Controllers;

import org.example.ecopoints_recycling_tracker.Dto.RecyclingEventDto;
import org.example.ecopoints_recycling_tracker.Entity.RecyclingEvent;
import org.example.ecopoints_recycling_tracker.Services.RecyclingService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class RecyclingEventContoller {
    public final RecyclingService recyclingService;

    public RecyclingEventContoller(RecyclingService recyclingService) {
        this.recyclingService = recyclingService;
    }

    @MutationMapping
    public RecyclingEvent addRecyclingEvent(@Argument RecyclingEventDto dto) {
        return recyclingService.addRecyclingEvent(dto);

    }
    @MutationMapping
    public Boolean DeleteRecyclingEvent(@Argument  int id){
        recyclingService.DeleteRecyclingEvent(id);
        return true;
    }
    @MutationMapping
    public Double CalculateTotalEcoPoints(@Argument float weight){
        return recyclingService.CalculateTotalEcoPoints(weight);
    }
    @MutationMapping
    public float TotalCommunityWeight(){
        return recyclingService.TotalCommunityWeight();
    }


}
