package org.example.sqbackend;

import org.example.sqbackend.models.Choice;
import org.example.sqbackend.models.Question;
import org.example.sqbackend.repositories.ChoiceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ChoiceRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ChoiceRepository choiceRepository;

    @Test
    public void testFindByQuestionIdQuestion() {
        // given
        Question question = new Question();
        entityManager.persist(question);

        Choice choice1 = new Choice();
        choice1.setQuestion(question);
        entityManager.persist(choice1);

        Choice choice2 = new Choice();
        choice2.setQuestion(question);
        entityManager.persist(choice2);

        entityManager.flush();

        // when
        List<Choice> foundChoices = choiceRepository.findByQuestionIdQuestion(question.getIdQuestion());

        // then
        assertThat(foundChoices).hasSize(2).extracting(Choice::getIdChoice).containsOnly(choice1.getIdChoice(), choice2.getIdChoice());
    }
}