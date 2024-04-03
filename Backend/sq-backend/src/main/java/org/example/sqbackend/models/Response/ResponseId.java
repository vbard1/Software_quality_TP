package org.example.sqbackend.models.Response;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.example.sqbackend.models.Choice;
import org.example.sqbackend.models.Spectator;

import java.io.Serializable;

@Embeddable
public class ResponseId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "id_spectator")
    private Spectator spectator;

    @ManyToOne
    @JoinColumn(name = "id_choice")
    private Choice choice;

    public Spectator getSpectator() {
        return spectator;
    }

    public void setSpectator(Spectator spectator) {
        this.spectator = spectator;
    }

    public Choice getChoice() {
        return choice;
    }

    public void setChoice(Choice choice) {
        this.choice = choice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResponseId that = (ResponseId) o;

        if (getSpectator() != null ? !getSpectator().equals(that.getSpectator()) : that.getSpectator() != null)
            return false;
        return getChoice() != null ? getChoice().equals(that.getChoice()) : that.getChoice() == null;
    }

    @Override
    public int hashCode() {
        int result = getSpectator() != null ? getSpectator().hashCode() : 0;
        result = 31 * result + (getChoice() != null ? getChoice().hashCode() : 0);
        return result;
    }
}
