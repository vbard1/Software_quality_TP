package org.example.sqbackend.repositories;

import org.example.sqbackend.models.Poll;
import org.example.sqbackend.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findByPoll(Poll poll);
}
