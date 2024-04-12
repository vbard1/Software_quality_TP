package org.example.sqbackend.services;

import org.example.sqbackend.models.Poll;
import org.example.sqbackend.models.Question;
import org.example.sqbackend.models.Spectator;
import org.springframework.stereotype.Service;

import java.util.List;

public interface QuestionService {
    List<Question> getAllQuestionsByPoll(Poll poll);

    List<Question> filterExpiredQuestions(List<Question> questions);

    List<Question> filterAnsweredQuestions(List<Question> questions, Spectator spectator);

    List<Question> getQuestionsByPollAndSpectator(Poll poll, Spectator spectator);

    List<Question> retrieveAllQuestionsFromPoll(Poll poll);
}
