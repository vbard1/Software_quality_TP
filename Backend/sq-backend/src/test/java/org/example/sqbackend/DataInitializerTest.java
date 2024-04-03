package org.example.sqbackend;

import org.example.sqbackend.initialization.DataInitializer;
import org.example.sqbackend.repositories.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DataInitializerTest {

    @Mock
    private SpectatorRepository spectatorRepository;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private PollRepository pollRepository;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private ChoiceRepository choiceRepository;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private ResponseRepository responseRepository;

    @InjectMocks
    private DataInitializer dataInitializer;

    @Test
    public void testInitialization() {
    }
}
