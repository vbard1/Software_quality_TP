package org.example.sqbackend.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Ticket {

    @Id
    @ManyToOne
    @JoinColumn(name = "id_spectator")
    private Spectator spectator;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_event")
    private Event event;

    @Column(nullable = false, unique = true)
    private String ticketNumber;

    private Date startDate;
    private Date endDate;

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
