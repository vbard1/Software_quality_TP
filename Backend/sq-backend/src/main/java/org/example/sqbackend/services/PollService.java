package org.example.sqbackend.services;

import org.example.sqbackend.models.Poll;

import java.util.Optional;

public interface PollService {
    public Poll findById(int id);
}
