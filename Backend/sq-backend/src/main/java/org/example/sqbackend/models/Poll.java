package org.example.sqbackend.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPoll;

    @ManyToOne
    @JoinColumn(name = "id_event")
    private Event event;

    private String name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Poll poll = (Poll) o;
        return idPoll == poll.idPoll &&
                Objects.equals(event, poll.event) &&
                Objects.equals(name, poll.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPoll, event, name);
    }
}
