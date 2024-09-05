
package org.example.sqbackend.controllers;

import org.example.sqbackend.models.Poll;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import org.example.sqbackend.models.Spectator;
import org.example.sqbackend.services.PollService;
import org.example.sqbackend.services.QuestionService;
import org.example.sqbackend.models.Question;
import org.example.sqbackend.services.SpectatorService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/polls")
public class PollController {

    private final QuestionService questionService;
    private final PollService pollService;
    private final SpectatorService spectatorService;

    public PollController(QuestionService questionService, PollService pollService, SpectatorService spectatorService) {
        this.questionService = questionService;
        this.pollService = pollService;
        this.spectatorService = spectatorService;
    }

    @GetMapping("/{pollId}/spectators/{spectatorId}/questions")
    public ResponseEntity<List<Question>> getQuestionsByPollAndSpectator(@PathVariable int pollId, @PathVariable int spectatorId) {
        Poll poll = pollService.getPollById(pollId);
        Spectator spectator = spectatorService.getSpectatorById(spectatorId); 
        List<Question> questions = questionService.getFilteredQuestionsByPollAndSpectator(poll, spectator);
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/polls/{spectatorId}")
    public ResponseEntity<List<Poll>> championshipsPage(@PathVariable int spectatorId) {
        List<Poll> polls = pollService.getAllPollsBySpectator(spectatorId);

        return ResponseEntity.ok(polls);
    }
}
