package org.example.sqbackend.models.Ticket;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.example.sqbackend.models.Event;
import org.example.sqbackend.models.Spectator;

import java.io.Serializable;

@Embeddable
public class TicketId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "id_spectator")
    private Spectator spectator;

    @ManyToOne
    @JoinColumn(name = "id_event")
    private Event event;

    public Spectator getSpectator() {
        return spectator;
    }

    public void setSpectator(Spectator spectator) {
        this.spectator = spectator;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TicketId ticketId = (TicketId) o;

        if (!getSpectator().equals(ticketId.getSpectator())) return false;
        return getEvent().equals(ticketId.getEvent());
    }

    @Override
    public int hashCode() {
        int result = getSpectator().hashCode();
        result = 31 * result + getEvent().hashCode();
        return result;
    }
}
