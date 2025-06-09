package com.app.playerservicejava.repository;
import com.app.playerservicejava.model.Player;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, String> {

    // No need for @Modifying or @Query for this use case.
    // Instead, implement the update logic in the service layer.
    // However, if you want a custom update method, you can use default methods in interface (Java 8+):

    @Modifying
    @Transactional
    @Query("UPDATE Player p SET p.firstName = :firstName, p.lastName = :lastName WHERE p.id = :id")
    int updatePlayerById(String id, String firstName, String lastName);
}
