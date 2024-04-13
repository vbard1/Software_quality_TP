package org.example.sqbackend;

import org.example.sqbackend.models.Choice;
import org.example.sqbackend.models.Poll;
import org.example.sqbackend.models.Question;
import org.example.sqbackend.models.Response.Response;
import org.example.sqbackend.models.Spectator;
import org.example.sqbackend.repositories.QuestionRepository;

import org.example.sqbackend.services.impl.QuestionServiceImpl;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class QuestionsServiceTest {
    @Mock
    private QuestionRepository questionsRepository;

    @InjectMocks
    private QuestionServiceImpl questionsService;

    @Test
    public void retrieve_all_question_from_poll(){
        // Setup
        List<Question> mockQuestions = new ArrayList<>();
        Question question1 = new Question();
        question1.setContent(generateRandomString(50));
        mockQuestions.add(question1);

        Question question2 = new Question();
        question2.setContent(generateRandomString(50));
        mockQuestions.add(question2);

        Question question3 = new Question();
        question3.setContent(generateRandomString(50));
        mockQuestions.add(question3);

        Poll testPoll = new Poll();
        when(questionsRepository.findByPoll(testPoll)).thenReturn(mockQuestions);

        // Test
        List<Question> result = questionsService.retrieveAllQuestionsFromPoll(testPoll);

        // Results
        assertEquals(3, result.size());
        assertEquals(mockQuestions.getFirst().getContent(), result.get(0).getContent());
        assertEquals(mockQuestions.get(1).getContent(), result.get(1).getContent());
        assertEquals(mockQuestions.get(2).getContent(), result.get(2).getContent());

        verify(questionsRepository, times(1)).findByPoll(testPoll);
    }

    @Test
    public void filter_out_expired_questions() {
        // Setup
        List<Question> inputQuestions = new ArrayList<>();
        Question question1 = new Question();
        question1.setContent(generateRandomString(50));
        question1.setExpirationDate(new Date(System.currentTimeMillis() - 3600000)); // Expired (1 hour ago)
        inputQuestions.add(question1);
        Question question2 = new Question();
        question2.setContent(generateRandomString(50));
        question2.setExpirationDate(new Date(System.currentTimeMillis() + 3600000)); // Not expired (1 hour from now)
        inputQuestions.add(question2);

        Question question3 = new Question();
        question3.setContent(generateRandomString(50));
        question3.setExpirationDate(new Date(System.currentTimeMillis() + 86400000)); // Not expired (1 day from now)
        inputQuestions.add(question3);

        // Test
        List<Question> result = questionsService.filterExpiredQuestions(inputQuestions);

        // Results
        assertEquals(2, result.size()); // Only 2 questions should remain after filtering
        assertEquals(inputQuestions.get(1).getContent(), result.get(0).getContent());
        assertEquals(inputQuestions.get(2).getContent(), result.get(1).getContent());
    }

    @Test
    public void filter_out_already_answered_questions() {
        // Setup
        List<Question> inputQuestions = new ArrayList<>();

        Question question1 = new Question();
        question1.setContent(generateRandomString(50));
        List<Choice> choices1 = new ArrayList<>();
        Choice choice1 = new Choice();
        choice1.setQuestion(question1);
        choice1.setContent(generateRandomString(50));
        choices1.add(choice1);
        Choice choice2 = new Choice();
        choice2.setQuestion(question1);
        choice2.setContent(generateRandomString(50));
        choices1.add(choice2);
        inputQuestions.add(question1);

        Question question2 = new Question();
        question2.setContent(generateRandomString(50));
        List<Choice> choices2 = new ArrayList<>();
        Choice choice3 = new Choice();
        choice3.setQuestion(question2);
        choice3.setContent(generateRandomString(50));
        choices2.add(choice3);
        Choice choice4 = new Choice();
        choice4.setQuestion(question2);
        choice4.setContent(generateRandomString(50));
        choices2.add(choice4);
        inputQuestions.add(question2);

        Spectator spectator1 = new Spectator();
        Response response1 = new Response();
        response1.setSpectator(spectator1);
        response1.setChoice(choice1);

        Spectator spectator2 = new Spectator(); // Checking if the spectator is taken into account
        Response response2 = new Response();
        response2.setSpectator(spectator2);
        response2.setChoice(choice3);

        // mock repository behaviour



        // Test
        List<Question> result = questionsService.filterAnsweredQuestions(inputQuestions, spectator1);

        // Results
        assertEquals(1, result.size());
        assertEquals(question2.getContent(),result.getFirst().getContent());
    }

    @Test
    public void getQuestionsByUserAndPollTest(){

    }

    @Test
    public void emptyResponseHandlingTest(){

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
}
