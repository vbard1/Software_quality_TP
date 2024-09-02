package org.example.sqbackend.services.impl;

import org.example.sqbackend.models.Poll;
import org.example.sqbackend.repositories.PollRepository;
import org.example.sqbackend.services.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PollServiceImpl implements PollService {

    private final PollRepository pollRepository;

    public PollServiceImpl(PollRepository pollRepository) {
        this.pollRepository = pollRepository;
    }

    @Override
    public Poll getPollById(int id) {
        return pollRepository.findById(id).orElse(null);
    }
}