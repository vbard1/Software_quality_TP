package org.example.sqbackend.repositories;

import org.example.sqbackend.models.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PollRepository extends JpaRepository<Poll, Integer> {
    @Query("SELECT p FROM Poll p JOIN Question q ON p.idPoll = q.poll.idPoll LEFT JOIN Choice c ON q.idQuestion = c.question.idQuestion LEFT JOIN Response r ON c.idChoice = r.id.choice.idChoice AND r.id.spectator.idSpectator = :spectatorId WHERE r.id.spectator.idSpectator IS NULL GROUP BY p.idPoll, p.name")
    List<Poll> getAllPollsBySpectator(Integer spectatorId);
}
