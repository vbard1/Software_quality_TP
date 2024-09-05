package org.example.sqbackend.services;

import org.example.sqbackend.models.Ticket.TicketId;
import org.example.sqbackend.models.Spectator;
public interface ConnectionService {
    Spectator findSpectatorByTicketNumber(String number);
}
