package org.example.sqbackend;

import org.example.sqbackend.models.Event;
import org.example.sqbackend.models.Poll;
import org.example.sqbackend.models.Question;
import org.example.sqbackend.repositories.PollRepository;
import org.example.sqbackend.services.impl.PollServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class PollRepositoryTests {

    @Mock
    private PollRepository pollRepository;

    @InjectMocks
    private PollServiceImpl pollServiceMock;

    @Test
    public void getPollBySpectator(){
        // Setup
        List<Question> inputQuestions = new ArrayList<>();
        Question question1 = new Question();
        question1.setContent(generateRandomString(50));
        inputQuestions.add(question1);

        Question question2 = new Question();
        question2.setContent(generateRandomString(50));
        inputQuestions.add(question2);

        Question question3 = new Question();
        question3.setContent(generateRandomString(50));
        inputQuestions.add(question3);

        Poll poll = new Poll();
    }

    @Test
    public void testGetPollsUserHasCompletedAllQuestions() {
        // Arrange: définir l'ID de l'utilisateur et une liste vide attendue
        int userId = 2;
        List<Poll> expectedPolls = Collections.emptyList();

        // Simuler la réponse du repository
        when(pollRepository.getAllPollsBySpectator(userId)).thenReturn(expectedPolls);

        // Act: appeler la méthode du service
        List<Poll> actualPolls = pollServiceMock.getAllPollsBySpectator(userId);

        // Assert: vérifier que les résultats sont ceux attendus (une liste vide)
        assertEquals(expectedPolls, actualPolls);
        verify(pollRepository, times(1)).getAllPollsBySpectator(userId);
    }

    @Test
    public void testEventInFuture() {
        // Arrange: définir l'ID de l'utilisateur et les sondages attendus
        int userId = 1;
        LocalDate localDateIn100Years = LocalDate.now().plusYears(100);
        Date dateIn100Years = Date.from(localDateIn100Years.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Event e1 = new Event(1, "Poll 1", dateIn100Years, dateIn100Years);

        Poll p1 = new Poll(1, e1, "Poll 1");
        Poll p2 = new Poll(2, e1, "Poll 2");
        List<Poll> expectedPolls = Arrays.asList(
                p1,
                p2
        );

        // Simuler la réponse du repository
        when(pollRepository.getAllPollsBySpectator(userId)).thenReturn(expectedPolls);

        // Act: appeler la méthode du service
        List<Poll> actualPolls = pollServiceMock.getAllPollsBySpectator(userId);

        // Assert: vérifier que les résultats sont ceux attendus
        assertEquals(Collections.emptyList(), actualPolls);
        verify(pollRepository, times(1)).getAllPollsBySpectator(userId);
    }

    private String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }

    private int generateRandomId() {
        return (int) (Math.random() * 999999);
    }
}
