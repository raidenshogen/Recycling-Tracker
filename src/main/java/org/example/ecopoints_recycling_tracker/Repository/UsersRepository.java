package org.example.ecopoints_recycling_tracker.Repository;

import org.example.ecopoints_recycling_tracker.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * NEW FILE. Add this alongside your existing HouseholdRepository /
 * RecyclingEventRepository. Because Household extends Users with
 * InheritanceType.JOINED, a query against the Users table returns the
 * correct concrete subtype (Household) automatically — you don't need
 * a separate lookup for households.
 */
public interface UsersRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByUsername(String username);
}
