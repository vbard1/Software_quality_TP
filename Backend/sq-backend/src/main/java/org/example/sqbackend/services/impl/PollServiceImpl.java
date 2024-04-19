package org.example.sqbackend.services.impl;

import org.example.sqbackend.models.Poll;
import org.example.sqbackend.repositories.PollRepository;
import org.example.sqbackend.services.PollService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PollServiceImpl implements PollService {
    private final PollRepository pollRepository;

    public PollServiceImpl(PollRepository pollRepository) {
        this.pollRepository = pollRepository;
    }

    @Override
    public Poll findById(int id) {
        return pollRepository.findById(id).orElse(null);
    }
}
