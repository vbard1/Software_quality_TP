package org.example.sqbackend.repositories;

import org.example.sqbackend.models.Choice;
import org.example.sqbackend.models.Response.Response;
import org.example.sqbackend.models.Spectator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseRepository extends JpaRepository<Response,Integer> {
    boolean existsByIdChoiceAndIdSpectator(Choice choice, Spectator spectator);
}
