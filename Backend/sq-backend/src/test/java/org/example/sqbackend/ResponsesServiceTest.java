package org.example.sqbackend;

import org.example.sqbackend.models.*;
import org.example.sqbackend.models.Response.Response;
import org.example.sqbackend.repositories.ChoiceRepository;
import org.example.sqbackend.repositories.QuestionRepository;
import org.example.sqbackend.repositories.ResponseRepository;
import org.example.sqbackend.repositories.SpectatorRepository;
import org.example.sqbackend.services.impl.QuestionServiceImpl;
import org.example.sqbackend.services.impl.ResponseServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class ResponsesServiceTest {

    @InjectMocks
    private ResponseServiceImpl responseService;

    @Mock
    private QuestionRepository questionsRepository;

    @Mock
    private ResponseRepository responseRepository;

    @Mock
    private ChoiceRepository choiceRepository;

    @Mock
    private SpectatorRepository spectatorRepository;
    @Mock
    private Choice choice;
    @Mock
    private Spectator spectator;

    @Test
    public void retrieve_all_responses_by_choice(){
        // Setup

        List<Spectator> mockSpectator = new ArrayList<>();
        Spectator spectator1 = new Spectator();
        spectator1.setIdSpectator(generateRandomId());
        mockSpectator.add(spectator1);
        Spectator spectator2 = new Spectator();
        spectator2.setIdSpectator(generateRandomId());
        mockSpectator.add(spectator2);

        Question question1 = new Question();
        question1.setIdQuestion(generateRandomId());
        question1.setContent(generateRandomString(50));

        List<Choice> mockChoices = new ArrayList<>();
        Choice choice1 = new Choice();
        choice1.setIdChoice(generateRandomId());
        choice1.setQuestion(question1);
        choice1.setContent(generateRandomString(50));
        mockChoices.add(choice1);
        Choice choice2 = new Choice();
        choice2.setIdChoice(generateRandomId());
        choice2.setQuestion(question1);
        choice2.setContent(generateRandomString(50));
        mockChoices.add(choice2);
        Choice choice3 = new Choice();
        choice3.setIdChoice(generateRandomId());
        choice3.setQuestion(question1);
        choice3.setContent(generateRandomString(50));
        mockChoices.add(choice3);

        List<Response> mockResponse = new ArrayList<>();
        Response response1 = new Response();
        response1.setSpectator(spectator1);
        response1.setChoice(choice1);
        mockResponse.add(response1);
        Response response2 = new Response();
        response2.setSpectator(spectator2);
        response2.setChoice(choice1);
        mockResponse.add(response2);

        when(choiceRepository.findByQuestionIdQuestion(question1.getIdQuestion())).thenReturn(mockChoices);

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
