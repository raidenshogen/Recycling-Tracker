package org.example.ecopoints_recycling_tracker.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(exclude = "recyclingEvents")
public class Household extends Users{
    private String fullName;
    private String country;
    private String address;
    private LocalDate JoinDate;
    private double TotalPoints;
    @OneToMany(mappedBy = "household", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RecyclingEvent> recyclingEvents = new ArrayList<>();

}
