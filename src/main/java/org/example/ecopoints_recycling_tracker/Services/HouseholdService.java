package org.example.ecopoints_recycling_tracker.Services;

import org.example.ecopoints_recycling_tracker.Dto.HouseholdDto;
import org.example.ecopoints_recycling_tracker.Entity.Household;
import org.example.ecopoints_recycling_tracker.Entity.RecyclingEvent;
import org.example.ecopoints_recycling_tracker.Exceptions.UserException;
import org.example.ecopoints_recycling_tracker.Mappers.HouseholdMapper;
import org.example.ecopoints_recycling_tracker.Repository.HouseholdRepository;
import org.example.ecopoints_recycling_tracker.Repository.RecyclingEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private final HouseholdRepository householdRepository;
    private final RecyclingEventRepository recyclingEventRepository;
    public final HouseholdMapper householdM;
    private final PasswordEncoder passwordEncoder;

    public HouseholdService(HouseholdRepository householdRepository, RecyclingEventRepository recyclingEventRepository, HouseholdMapper householdM, PasswordEncoder passwordEncoder) {
        this.householdRepository = householdRepository;
        this.recyclingEventRepository = recyclingEventRepository;
        this.householdM = householdM;
        this.passwordEncoder = passwordEncoder;
    }

    public Household CreateHousehold(HouseholdDto householdD) {
        Household household = householdM.toEntity(householdD);
        if(householdRepository.existsByEmail(household.getEmail())){
            throw new UserException("Email already exists");}
        // Without this, CustomUserDetailsService/login would compare
        // BCrypt hashes against the raw password and always fail.
        household.setPassword(passwordEncoder.encode(household.getPassword()));
        householdRepository.save(household);
        Map<Integer,Household> mapHouseholders = new HashMap<>();
        mapHouseholders.put(household.getId(), household);

        return household;
    }

    public double CalculateTotalPoints(int id) {
        List<RecyclingEvent> recyclingEventList=recyclingEventRepository.findByHousehold_Id(id);
        double points=0;
        if (recyclingEventList.isEmpty()) {
            System.out.println("No recycling events found");
        }else  {

            for (RecyclingEvent recyclingEvent : recyclingEventList) {
                points += recyclingEvent.getEcoPoints();

            }
        }
        Household household=householdRepository.findById(id).orElse(null);
        household.setTotalPoints(points);
        return  points;
    }

    public Boolean SavelogsForHouseholds() throws IOException {

        FileWriter fw = new FileWriter("logs");

        try (BufferedWriter bw = new BufferedWriter(fw)) {

            bw.write("List of Households With their Recycling events:");
            bw.newLine();

            int count = 1;

            for (Household household1 : householdRepository.findAll()) {

                bw.write("Household " + count + ":");
                bw.newLine();

                bw.write("Name: " + household1.getFullName());
                bw.newLine();

                bw.write("Address: " + household1.getAddress());
                bw.newLine();

                bw.write("Join Date: " + household1.getJoinDate());
                bw.newLine();

                bw.write("Total Points: " + household1.getTotalPoints());
                bw.newLine();

                int count2 = 1;

                for (RecyclingEvent recyclingEvent : household1.getRecyclingEvents()) {

                    bw.write("Recycling Event " + count2 + ":");
                    bw.newLine();

                    bw.write("Material: " + recyclingEvent.getMatType());
                    bw.newLine();

                    bw.write("Weight in KG: " + recyclingEvent.getWeightKG());
                    bw.newLine();

                    bw.write("Recycling Date: " + recyclingEvent.getRecyclingDate());
                    bw.newLine();

                    bw.write("Eco points: " + recyclingEvent.getEcoPoints());
                    bw.newLine();

                    count2++;
                }

                bw.newLine();
                count++;
            }

            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<RecyclingEvent> findAllRecyclingEventsByHouseholdId(int id) {
        List<RecyclingEvent> lre=recyclingEventRepository.findByHousehold_Id(id);
        return lre;
    }
    public float CalculateTotalWeights(int id ) {
        Household household =householdRepository.findById(id).orElse(null);
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
        if (mapHouseholds.isEmpty()) {
            return null;
        }

        Household h1 = mapHouseholds.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();

        return h1;
    }


}