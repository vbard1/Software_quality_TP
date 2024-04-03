package org.example.sqbackend.models.Response;

import jakarta.persistence.*;
import org.example.sqbackend.models.Choice;
import org.example.sqbackend.models.Spectator;

@Entity
@Table(name = "response")
public class Response {
    @EmbeddedId
    private ResponseId id;

    public Response() {
        this.id = new ResponseId();
    }

    public Spectator getSpectator() {
        return this.id.getSpectator();
    }

    public void setSpectator(Spectator spectator) {
        this.id.setSpectator(spectator);
    }

    public Choice getChoice() {
        return this.id.getChoice();
    }

    public void setChoice(Choice choice) {
        this.id.setChoice( choice);
    }
}
