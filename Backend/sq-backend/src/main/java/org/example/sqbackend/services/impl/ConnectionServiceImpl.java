package org.example.sqbackend.services.impl;

import org.example.sqbackend.exceptions.InvalidTicketIdException;
import org.example.sqbackend.exceptions.OutOfDateTicketException;
import org.example.sqbackend.models.Spectator;
import org.example.sqbackend.models.Ticket.Ticket;
import org.example.sqbackend.models.Ticket.TicketId;
import org.example.sqbackend.repositories.*;
import org.example.sqbackend.services.ConnectionService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ConnectionServiceImpl implements ConnectionService {
    private final TicketRepository ticketRepository;
    public ConnectionServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }
    @Override
    public Spectator findSpectatorByTicketNumber(String number) {
        if (number == null)
            throw new NullPointerException("The given ticket number is null");

        Ticket ticket = ticketRepository.findTicketByTicketNumber(number);

        if (ticket == null)
            throw new InvalidTicketIdException("The ticket number is not valid");

        Date currentDate = new Date();
        if (ticket.getEndDate().before(currentDate))
            throw new OutOfDateTicketException("The event is finished, and the ticket has expired");

        return ticket.getSpectator();
    }
}
