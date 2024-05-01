package org.example.sqbackend.repositories;

import org.example.sqbackend.models.Spectator;
import org.example.sqbackend.models.Ticket.Ticket;
import org.example.sqbackend.models.Ticket.TicketId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    Ticket findTicketByTicketNumber(String ticketNumber);
}
