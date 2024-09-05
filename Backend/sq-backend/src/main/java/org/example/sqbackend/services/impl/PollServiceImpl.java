package org.example.sqbackend.services.impl;

import org.example.sqbackend.models.Poll;
import org.example.sqbackend.repositories.PollRepository;
import org.example.sqbackend.services.PollService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PollServiceImpl implements PollService {

    private final PollRepository pollRepository;

    public PollServiceImpl(PollRepository pollRepository) {
        this.pollRepository = pollRepository;
    }

    @Override
    public List<Poll> getAllPollsBySpectator(int spectatorId) {
        List<Poll> polls = pollRepository.getAllPollsBySpectator(spectatorId)
                .stream()
                .filter(poll -> {
                    // Conversion de Date à LocalDate
                    LocalDate eventEndDate = poll.getEvent().getEndDate().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();

                    // Filtrer les événements passés ou en cours
                    return eventEndDate.isBefore(LocalDate.now()) || eventEndDate.isEqual(LocalDate.now());
                })
                .collect(Collectors.toList());
        return polls;
    }
}
