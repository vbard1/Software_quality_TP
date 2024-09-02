package org.example.sqbackend.services;

import org.example.sqbackend.models.Poll;

public interface PollService {
    Poll getPollById(int id);
}