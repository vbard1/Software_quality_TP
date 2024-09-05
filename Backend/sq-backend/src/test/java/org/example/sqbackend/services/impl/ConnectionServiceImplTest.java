package org.example.sqbackend.services.impl;

import org.example.sqbackend.exceptions.InvalidTicketIdException;
import org.example.sqbackend.exceptions.OutOfDateTicketException;
import org.example.sqbackend.models.Spectator;
import org.example.sqbackend.models.Ticket.Ticket;
import org.example.sqbackend.models.Ticket.TicketId;
import org.example.sqbackend.repositories.TicketRepository;
import org.example.sqbackend.services.ConnectionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
class ConnectionServiceImplTest {

    @Mock
    private TicketRepository ticketRepository;
    @InjectMocks
    private ConnectionServiceImpl connectionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testThrowsNullTicketError() {
        // Given
        String number = null;

        // When/Then
        assertThrows(NullPointerException.class, () -> connectionService.findSpectatorByTicketNumber(number));
    }
    @Test
    public void testRetrieveSpectatorForTicket() {
        // Given
        String ticketnumber = "1256214856a55fazdfaz";
        Spectator spectator = new Spectator();
        Ticket ticket = new Ticket();

        ticket.setSpectator(spectator);
        ticket.setEndDate(new Date(2024,9,9));

        when(ticketRepository.findTicketByTicketNumber(ticketnumber)).thenReturn(ticket);

        // When
        Spectator actualSpectator = connectionService.findSpectatorByTicketNumber(ticketnumber);

        // Then
        assertEquals(spectator, actualSpectator);
    }

    @Test
    public void testThrowErrorIfTicketIdIsWrong() {
        // Given
        String ticket = "0az0f0az0az0az0g0z";

        when(ticketRepository.findTicketByTicketNumber(ticket)).thenReturn(null);

        // When/then
        assertThrows(InvalidTicketIdException.class, () -> connectionService.findSpectatorByTicketNumber(ticket));
    }

    @Test
    public void testThrowErrorForOutOfDateTicketId() {
        // Given
        String ticketNumber = "afazfazefaezfazefaez";
        Ticket ticket = new Ticket();
        Date endDate = new Date(0);
        endDate.setYear(-1900);
        endDate.setMonth(0);
        endDate.setDate(9);
        ticket.setEndDate(endDate);

        when(ticketRepository.findTicketByTicketNumber(ticketNumber)).thenReturn(ticket);

        // When/Then
        assertThrows(OutOfDateTicketException.class, () -> connectionService.findSpectatorByTicketNumber(ticketNumber));
    }
}