package org.example.ecopoints_recycling_tracker.Controllers;

import org.example.ecopoints_recycling_tracker.Dto.HouseholdDto;
import org.example.ecopoints_recycling_tracker.Entity.Household;
import org.example.ecopoints_recycling_tracker.Entity.RecyclingEvent;
import org.example.ecopoints_recycling_tracker.Repository.HouseholdRepository;
import org.example.ecopoints_recycling_tracker.Services.HouseholdService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

@Controller
public class HouseholdController {
    private HouseholdRepository holdRepo;
    private final HouseholdService householdService;

    public HouseholdController(HouseholdService householdService) {
        this.householdService = householdService;
    }

    // Public — this IS how a household signs up, so it can't require auth.
    @MutationMapping
    public Household CreateHousehold(@Argument HouseholdDto household) {
        return householdService.CreateHousehold(household);
    }

    // Any logged-in user or admin can check a total.
    @PreAuthorize("isAuthenticated()")
    @MutationMapping
    public double CalculateTotalPoints(@Argument int id ) {
        return householdService.CalculateTotalPoints(id);
    }

    // Writes to a file on the server — admin only.
    @PreAuthorize("hasRole('ADMIN')")
    @MutationMapping
    public Boolean SavelogsForHouseholds() throws IOException {
        householdService.SavelogsForHouseholds();
        return true;
    }

    @PreAuthorize("isAuthenticated()")
    @QueryMapping
    public List<RecyclingEvent> findAllRecyclingEventsByHouseholdId(@Argument int id){
        return householdService.findAllRecyclingEventsByHouseholdId(id);
    }

    // Community leaderboard — fine for any signed-in user to view.
    @PreAuthorize("isAuthenticated()")
    @QueryMapping
    public Household HouseholdHaveHighestTotalPoints(){
        return householdService.HighestTotalPoints();
    }

    @PreAuthorize("isAuthenticated()")
    @MutationMapping
    public float CalculateTotalWeights(@Argument int id){
        return householdService.CalculateTotalWeights(id);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @QueryMapping
    public List<Household> findAllHouseholds() {
        return holdRepo.findAll();
    }




}