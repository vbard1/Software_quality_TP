package org.example.sqbackend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Response {

    @Id
    @ManyToOne
    @JoinColumn(name = "id_spectator")
    private Spectator spectator;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_choice")
    private Choice choice;
}
