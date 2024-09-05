package org.example.sqbackend.services;

import org.example.sqbackend.models.Poll;

import java.util.List;

public interface PollService {
    List<Poll> getAllPollsBySpectator(int spectatorId);
}
