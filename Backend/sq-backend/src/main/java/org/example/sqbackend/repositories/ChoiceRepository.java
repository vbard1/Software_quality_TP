package org.example.sqbackend.repositories;

import org.example.sqbackend.models.Choice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.SequencedCollection;

@Repository
public interface ChoiceRepository extends JpaRepository<Choice, Integer> {
    List<Choice> findByQuestionIdQuestion(int questionId);
}
