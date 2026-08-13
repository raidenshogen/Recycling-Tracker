package org.example.ecopoints_recycling_tracker.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tools.jackson.databind.ser.jdk.JDKKeySerializers;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data

@Inheritance(strategy = InheritanceType.JOINED)
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private String email;
    @Enumerated(EnumType.STRING)
    private Roles role=Roles.USER;


}
