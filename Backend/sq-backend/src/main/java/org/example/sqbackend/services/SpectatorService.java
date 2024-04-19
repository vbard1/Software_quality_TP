package org.example.sqbackend.services;

import org.example.sqbackend.models.Spectator;

import java.util.Optional;

public interface SpectatorService {
    public Spectator findById(int id);
}
