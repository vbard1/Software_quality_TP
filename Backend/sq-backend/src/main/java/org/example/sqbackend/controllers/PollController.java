package org.example.sqbackend.controllers;

import org.example.sqbackend.models.Poll;
import org.example.sqbackend.repositories.PollRepository;
import org.example.sqbackend.services.impl.PollServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PollController {

    @Autowired
    private PollRepository pollRepository;
    @Autowired
    private PollServiceImpl pollServiceImpl;

    @GetMapping("/polls/{spectatorId}")
    public ResponseEntity<List<Poll>> championshipsPage(@PathVariable int spectatorId) {
        List<Poll> polls = pollServiceImpl.getAllPollsBySpectator(spectatorId);

        return ResponseEntity.ok(polls);
    }
}
