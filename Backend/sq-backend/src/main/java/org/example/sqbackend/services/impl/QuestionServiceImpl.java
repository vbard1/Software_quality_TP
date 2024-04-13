package org.example.sqbackend.services.impl;

import jdk.jshell.spi.ExecutionControl;
import org.example.sqbackend.models.Choice;
import org.example.sqbackend.models.Poll;
import org.example.sqbackend.models.Question;
import org.example.sqbackend.models.Spectator;
import org.example.sqbackend.repositories.ChoiceRepository;
import org.example.sqbackend.repositories.PollRepository;
import org.example.sqbackend.repositories.QuestionRepository;
import org.example.sqbackend.repositories.ResponseRepository;
import org.example.sqbackend.services.QuestionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final ResponseRepository responseRepository;

    private final ChoiceRepository choiceRepository;
    private final PollRepository pollRepository;
    public QuestionServiceImpl(QuestionRepository questionRepository, PollRepository pollRepository, ResponseRepository responseRepository, ChoiceRepository choiceRepository) {
        this.questionRepository=questionRepository;
        this.pollRepository=pollRepository;
        this.responseRepository = responseRepository;
        this.choiceRepository = choiceRepository;
    }

    /**
     * @param poll
     * @return
     */
    @Override
    public List<Question> getAllQuestionsByPoll(Poll poll) {
        // TODO test
        return null;
    }

    /**
     * Filters expired questions from the given list.
     *
     * @param questions The list of questions to filter.
     * @return A list of non-expired questions.
     */
    public List<Question> filterExpiredQuestions(List<Question> questions) {
        return questions.stream()
                .filter(question -> !isQuestionExpired(question))
                .collect(Collectors.toList());
    }

    /**
     * Checks if a question is expired based on the current date.
     *
     * @param question   The question to check.
     * @return True if the question is expired, false otherwise.
     */
    public boolean isQuestionExpired(Question question) {
        return question.getExpirationDate().before(new Date());
    }

    /**
     * @param questions
     * @param spectator
     * @return
     */
    @Override
    public List<Question> filterAnsweredQuestions(List<Question> questions, Spectator spectator) {
        return questions.stream()
                .filter(question -> {
                    List<Choice> choices = choiceRepository.findByQuestionIdQuestion(question.getIdQuestion());
                    if (choices == null || choices.isEmpty()) {
                        System.out.println("Warning: No choices found for question with ID: " + question.getIdQuestion());
                        return false;
                    }
                    return choices.stream()
                            .noneMatch(choice -> responseRepository.existsByIdChoiceAndIdSpectator(choice, spectator));
                })
                .collect(Collectors.toList());
    }

    /**
     * @param poll
     * @param spectator
     * @return
     */
    @Override
    public List<Question> getQuestionsByPollAndSpectator(Poll poll, Spectator spectator) {
        // TODO test
        return null;
    }

    /**
     * @param poll
     * @return
     */
    @Override
    public List<Question> retrieveAllQuestionsFromPoll(Poll poll) {
        return questionRepository.findByPoll(poll);
    }
}
