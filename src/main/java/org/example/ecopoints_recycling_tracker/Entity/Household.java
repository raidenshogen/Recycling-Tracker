package org.example.ecopoints_recycling_tracker.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Household extends User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String address;
    private Date JoinDate;
    private double TotalPoints;

    @OneToMany(mappedBy = "household", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RecyclingEvent> recyclingEvents = new ArrayList<>();

}
