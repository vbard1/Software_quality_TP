package org.example.sqbackend.models.Ticket;

import jakarta.persistence.*;
import org.example.sqbackend.models.Event;
import org.example.sqbackend.models.Spectator;

import java.util.Date;

@Entity
@Table(name = "ticket")
public class Ticket {
    @EmbeddedId
    private TicketId id;

    @Column(nullable = false, unique = true)
    private String ticketNumber;

    private Date startDate;
    private Date endDate;

    public Ticket() {
        this.id = new TicketId();
    }
    public Spectator getSpectator() {
        return id.getSpectator();
    }

    public void setSpectator(Spectator spectator) {
        this.id.setSpectator(spectator);
    }

    public Event getEvent() {
        return this.id.getEvent();
    }

    public void setEvent(Event event) {
        this.id.setEvent(event);
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
