package org.example.sqbackend;

import org.example.sqbackend.models.Poll;
import org.example.sqbackend.models.Question;
import org.example.sqbackend.repositories.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class QuestionRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    public void testFindByPoll() {
        // given
        Poll poll = new Poll();
        entityManager.persist(poll);

        Question question1 = new Question();
        question1.setPoll(poll);
        entityManager.persist(question1);

        Question question2 = new Question();
        question2.setPoll(poll);
        entityManager.persist(question2);

        entityManager.flush();

        // when
        List<Question> foundQuestions = questionRepository.findByPoll(poll);

        // then
        assertThat(foundQuestions).hasSize(2).extracting(Question::getIdQuestion).containsOnly(question1.getIdQuestion(), question2.getIdQuestion());
    }
}