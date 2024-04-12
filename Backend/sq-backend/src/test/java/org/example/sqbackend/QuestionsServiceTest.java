package org.example.sqbackend;

import org.example.sqbackend.models.Poll;
import org.example.sqbackend.models.Question;
import org.example.sqbackend.repositories.QuestionRepository;
import org.example.sqbackend.services.QuestionService;

import org.example.sqbackend.services.impl.QuestionServiceImpl;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;
import org.junit.Test;

import java.time.LocalDateTime;
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
    public void expiredQuestionsHandlingTest() {
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
    public void answeredQuestionsHandlingTest(){

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
