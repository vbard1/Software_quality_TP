package org.example.sqbackend;

import org.example.sqbackend.models.*;
import org.example.sqbackend.models.Response.Response;
import org.example.sqbackend.repositories.ChoiceRepository;
import org.example.sqbackend.repositories.QuestionRepository;

import org.example.sqbackend.repositories.ResponseRepository;
import org.example.sqbackend.services.impl.QuestionServiceImpl;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;
import org.junit.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class QuestionsServiceTest {
    @Mock
    private QuestionRepository questionsRepository;

    @InjectMocks
    private QuestionServiceImpl questionsService;

    @Mock
    private ChoiceRepository choiceRepository;

    @Mock
    private ResponseRepository responseRepository;

    @Test
    public void retrieve_all_question_from_poll(){
        // Setup
        List<Question> mockQuestions = new ArrayList<>();
        Question question1 = new Question();
        question1.setContent(GeneratedStringUtils.generateRandomString(50));
        mockQuestions.add(question1);

        Question question2 = new Question();
        question2.setContent(GeneratedStringUtils.generateRandomString(50));
        mockQuestions.add(question2);

        Question question3 = new Question();
        question3.setContent(GeneratedStringUtils.generateRandomString(50));
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
        question1.setContent(GeneratedStringUtils.generateRandomString(50));
        question1.setExpirationDate(new Date(System.currentTimeMillis() - 3600000)); // Expired (1 hour ago)
        inputQuestions.add(question1);
        Question question2 = new Question();
        question2.setContent(GeneratedStringUtils.generateRandomString(50));
        question2.setExpirationDate(new Date(System.currentTimeMillis() + 3600000)); // Not expired (1 hour from now)
        inputQuestions.add(question2);

        Question question3 = new Question();
        question3.setContent(GeneratedStringUtils.generateRandomString(50));
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
        question1.setIdQuestion(GeneratedStringUtils.generateRandomId());
        question1.setContent(GeneratedStringUtils.generateRandomString(50));
        List<Choice> choices1 = new ArrayList<>();
        Choice choice1 = new Choice();
        choice1.setIdChoice(GeneratedStringUtils.generateRandomId());
        choice1.setQuestion(question1);
        choice1.setContent(GeneratedStringUtils.generateRandomString(50));
        choices1.add(choice1);
        Choice choice2 = new Choice();
        choice2.setIdChoice(GeneratedStringUtils.generateRandomId());
        choice2.setQuestion(question1);
        choice2.setContent(GeneratedStringUtils.generateRandomString(50));
        choices1.add(choice2);
        inputQuestions.add(question1);

        Question question2 = new Question();
        question2.setIdQuestion(GeneratedStringUtils.generateRandomId());
        question2.setContent(GeneratedStringUtils.generateRandomString(50));
        List<Choice> choices2 = new ArrayList<>();
        Choice choice3 = new Choice();
        choice3.setIdChoice(GeneratedStringUtils.generateRandomId());
        choice3.setQuestion(question2);
        choice3.setContent(GeneratedStringUtils.generateRandomString(50));
        choices2.add(choice3);
        Choice choice4 = new Choice();
        choice4.setIdChoice(GeneratedStringUtils.generateRandomId());
        choice4.setQuestion(question2);
        choice4.setContent(GeneratedStringUtils.generateRandomString(50));
        choices2.add(choice4);
        inputQuestions.add(question2);

        Question question3 = new Question();
        question3.setContent(GeneratedStringUtils.generateRandomString(50));
        question3.setIdQuestion(GeneratedStringUtils.generateRandomId());
        List<Choice> choices3 = new ArrayList<>();
        Choice choice5 = new Choice();
        choice5.setIdChoice(GeneratedStringUtils.generateRandomId());
        choice5.setQuestion(question3);
        choice5.setContent(GeneratedStringUtils.generateRandomString(50));
        choices3.add(choice5);
        Choice choice6 = new Choice();
        choice6.setIdChoice(GeneratedStringUtils.generateRandomId());
        choice6.setQuestion(question3);
        choice6.setContent(GeneratedStringUtils.generateRandomString(50));
        choices3.add(choice6);
        inputQuestions.add(question3);

        Spectator spectator1 = new Spectator();
        spectator1.setIdSpectator(GeneratedStringUtils.generateRandomId());
        Response response1 = new Response();
        response1.setSpectator(spectator1);
        response1.setChoice(choice1);
        Response response2 = new Response();
        response2.setSpectator(spectator1);
        response2.setChoice(choice4);


        // mock repository behaviour
        when(choiceRepository.findByQuestionIdQuestion(question1.getIdQuestion())).thenReturn(choices1);
        when(choiceRepository.findByQuestionIdQuestion(question2.getIdQuestion())).thenReturn(choices2);
        when(choiceRepository.findByQuestionIdQuestion(question3.getIdQuestion())).thenReturn(choices3);
        when(responseRepository.existsByIdChoiceAndIdSpectator(any(Choice.class), any(Spectator.class))).thenAnswer(invocation -> {
            Choice choice = invocation.getArgument(0);
            Spectator spectator = invocation.getArgument(1);

            // Check if a response exists for the given choice and spectator
            if ((choice.equals(choice1)||choice.equals(choice4)) && spectator.equals(spectator1)) {
                return true; // Response exists for question 1 and 2 and spectator 1
            } else {
                return false;
            }
        });


        // Test
        List<Question> result = questionsService.filterAnsweredQuestions(inputQuestions, spectator1);

        // Results
        assertEquals(1, result.size());
        assertEquals(question3.getContent(),result.getFirst().getContent());
    }

    @Test
    public void get_filtered_questions_by_user_and_poll_test(){
        Event event = new Event();
        event.setName("lol");
        event.setStartDate(new Date());
        event.setEndDate(new Date());
        event.setIdEvent(1);

        // Setup
        Poll poll = new Poll();
        poll.setIdPoll(1);
        poll.setEvent(event);

        Question question1 = new Question();
        question1.setIdQuestion(GeneratedStringUtils.generateRandomId());
        question1.setExpirationDate(new Date(System.currentTimeMillis() - 3600000)); // Expired (1 hour ago)
        question1.setContent(GeneratedStringUtils.generateRandomString(50));
        question1.setPoll(poll);

        List<Choice> choices1 = new ArrayList<>();
        Choice choice1 = new Choice();
        choice1.setIdChoice(GeneratedStringUtils.generateRandomId());
        choice1.setQuestion(question1);
        choice1.setContent(GeneratedStringUtils.generateRandomString(50));
        choices1.add(choice1);

        Question question2 = new Question();
        question2.setIdQuestion(GeneratedStringUtils.generateRandomId());
        question2.setContent(GeneratedStringUtils.generateRandomString(50));
        question2.setExpirationDate(new Date());
        question2.setPoll(poll);
        question2.setExpirationDate(new Date(System.currentTimeMillis() - 3600000)); // Expired (1 hour ago)

        List<Choice> choices2 = new ArrayList<>();
        Choice choice3 = new Choice();
        choice3.setIdChoice(GeneratedStringUtils.generateRandomId());
        choice3.setQuestion(question2);
        choice3.setContent(GeneratedStringUtils.generateRandomString(50));
        choices2.add(choice3);

        Question question3 = new Question();
        question3.setContent(GeneratedStringUtils.generateRandomString(50));
        question3.setIdQuestion(GeneratedStringUtils.generateRandomId());
        question3.setExpirationDate(new Date(System.currentTimeMillis() + 3600000)); // Not expired (1 hour from now)
        question3.setPoll(poll);

        List<Choice> choices3 = new ArrayList<>();
        Choice choice5 = new Choice();
        choice5.setIdChoice(GeneratedStringUtils.generateRandomId());
        choice5.setQuestion(question3);
        choice5.setContent(GeneratedStringUtils.generateRandomString(50));
        choices3.add(choice5);

        Choice choice6 = new Choice();
        choice6.setIdChoice(GeneratedStringUtils.generateRandomId());
        choice6.setQuestion(question3);
        choice6.setContent(GeneratedStringUtils.generateRandomString(50));
        choices3.add(choice6);

        Spectator spectator1 = new Spectator();
        spectator1.setIdSpectator(GeneratedStringUtils.generateRandomId());

        Response response1 = new Response();
        response1.setSpectator(spectator1);
        response1.setChoice(choice1);

        // mock repository behaviour
        when(questionsRepository.findByPoll(poll)).thenReturn(Arrays.asList(question1,question2,question3));
        when(choiceRepository.findByQuestionIdQuestion(question1.getIdQuestion())).thenReturn(choices1);
        when(choiceRepository.findByQuestionIdQuestion(question2.getIdQuestion())).thenReturn(choices2);
        when(choiceRepository.findByQuestionIdQuestion(question3.getIdQuestion())).thenReturn(choices3);
        when(responseRepository.existsByIdChoiceAndIdSpectator(any(Choice.class), any(Spectator.class))).thenAnswer(invocation -> {
            Choice choice = invocation.getArgument(0);
            Spectator spectator = invocation.getArgument(1);

            // Check if a response exists for the given choice and spectator
            if ((choice.equals(choice1)) && spectator.equals(spectator1)) {
                return true; // Response exists for question 1 and spectator 1
            } else {
                return false;
            }
        });


        // Test
        List<Question> result = questionsService.getFilteredQuestionsByPollAndSpectator(poll, spectator1);

        // Results
        assertEquals(1, result.size());
        assertEquals(question3.getContent(),result.getFirst().getContent());
    }
}
