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
    public void testGetPollsUserHasCompletedAllQuestions() {
        int userId = 2;
        List<Poll> expectedPolls = Collections.emptyList();

        when(pollRepository.getAllPollsBySpectator(userId)).thenReturn(expectedPolls);

        List<Poll> actualPolls = pollServiceMock.getAllPollsBySpectator(userId);

        assertEquals(expectedPolls, actualPolls);
        verify(pollRepository, times(1)).getAllPollsBySpectator(userId);
    }

    @Test
    public void testEventInFuture() {
        int userId = 1;
        LocalDate localDateIn100Years = LocalDate.now().plusYears(100);
        Date dateIn100Years = Date.from(localDateIn100Years.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Event e1 = new Event(1, "Poll 1", dateIn100Years, dateIn100Years);

        List<Poll> expectedPolls = Arrays.asList(
            new Poll(1, e1, "Poll 1"),
            new Poll(2, e1, "Poll 2")
        );

        when(pollRepository.getAllPollsBySpectator(userId)).thenReturn(expectedPolls);

        List<Poll> actualPolls = pollServiceMock.getAllPollsBySpectator(userId);

        assertEquals(Collections.emptyList(), actualPolls);
        verify(pollRepository, times(1)).getAllPollsBySpectator(userId);
    }
}
