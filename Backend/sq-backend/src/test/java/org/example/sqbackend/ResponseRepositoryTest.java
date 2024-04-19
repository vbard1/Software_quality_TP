package org.example.sqbackend;

import org.example.sqbackend.models.Choice;
import org.example.sqbackend.models.Response.Response;
import org.example.sqbackend.models.Spectator;
import org.example.sqbackend.repositories.ResponseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ResponseRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ResponseRepository responseRepository;

    @Test
    public void testExistsByIdChoiceAndIdSpectator() {
        // given
        Choice choice = new Choice();
        entityManager.persist(choice);

        Spectator spectator = new Spectator();
        spectator.setEmail("a@a.com");
        entityManager.persist(spectator);

        Response response = new Response();
        response.setChoice(choice);
        response.setSpectator(spectator);
        entityManager.persist(response);

        entityManager.flush();

        // when
        boolean exists = responseRepository.existsByIdChoiceAndIdSpectator(choice, spectator);

        // then
        assertThat(exists).isTrue();
    }
}