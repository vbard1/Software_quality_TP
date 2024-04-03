package org.example.sqbackend.repositories;

import org.example.sqbackend.models.Spectator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpectatorRepository extends JpaRepository<Spectator,Integer> {
}
