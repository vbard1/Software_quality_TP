package org.example.sqbackend.services.impl;

import org.example.sqbackend.models.Spectator;
import org.example.sqbackend.repositories.SpectatorRepository;
import org.example.sqbackend.services.SpectatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpectatorServiceImpl implements SpectatorService {

    private final SpectatorRepository spectatorRepository;

    public SpectatorServiceImpl(SpectatorRepository spectatorRepository) {
        this.spectatorRepository = spectatorRepository;
    }

    @Override
    public Spectator getSpectatorById(int id) {
        return spectatorRepository.findById(id).orElse(null);
    }
}