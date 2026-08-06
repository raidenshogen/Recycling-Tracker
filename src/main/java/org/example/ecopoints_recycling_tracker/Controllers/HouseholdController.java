package org.example.ecopoints_recycling_tracker.Controllers;

import org.example.ecopoints_recycling_tracker.Entity.Household;
import org.example.ecopoints_recycling_tracker.Entity.RecyclingEvent;
import org.example.ecopoints_recycling_tracker.Services.HouseholdService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

@Controller
public class HouseholdController {
    private final HouseholdService householdService;

    public HouseholdController(HouseholdService householdService) {
        this.householdService = householdService;
    }

    @MutationMapping
    public Household CreateHousehold(@Argument Household household) {
    return householdService.CreateHousehold(household);
    }
    @MutationMapping
    public double CalculateTotalPoints(@Argument Household household) {
        return householdService.CalculateTotalPoints(household);
    }
    @MutationMapping
    public Boolean SavelogsForHouseholds() throws IOException {
        householdService.SavelogsForHouseholds();
        return true;
    }
    @QueryMapping
    public List<RecyclingEvent> findAllRecyclingEventsByHouseholdId(@Argument int id){
        return householdService.findAllRecyclingEventsByHouseholdId(id);
    }
    @QueryMapping
    public Household HouseholdHaveHighestTotalPoints(){
        return householdService.HighestTotalPoints();
    }




}
