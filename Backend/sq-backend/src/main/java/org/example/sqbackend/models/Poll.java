package org.example.sqbackend.models;

import jakarta.persistence.*;

@Entity
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPoll;

    @ManyToOne
    @JoinColumn(name = "id_event")
    private Event event;

    public int getIdPoll() {
        return idPoll;
    }

    public void setIdPoll(int idPoll) {
        this.idPoll = idPoll;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
