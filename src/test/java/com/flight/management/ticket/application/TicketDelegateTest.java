package com.flight.management.ticket.application;

import com.flight.management.ticket.domain.Passenger;
import com.flight.management.ticket.domain.Ticket;
import com.flight.management.ticket.domain.TicketAggregate;
import com.flight.management.ticket.domain.TicketPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class TicketDelegateTest {

    @InjectMocks
    private TicketDelegate delegate;

    @Mock
    private TicketPort port;

    @Test
    @DisplayName("Test findById Success")
    void testFindById() {
        TicketAggregate ticket = TicketAggregate.builder()
                .ticket(Ticket.builder()
                        .id(1L)
                        .arrivalDate(LocalDate.of(2021,6,16))
                        .arrivalTime(LocalTime.of(20,0,0))
                        .departureDate(LocalDate.of(2021,6,16))
                        .departureTime(LocalTime.of(10,0,0))
                        .srcCity("CIUDAD DE MEXICO")
                        .trgCity("PARIS")
                        .build())
                .passenger(Passenger.builder()
                        .name("Pablo Ruiz")
                        .age((short)25)
                        .build())
                .build();
        doReturn(Optional.of(ticket)).when(port).findTicketByID(1l);

        TicketResponse returnedTicket = delegate.findTicketById(1l);
        Assertions.assertAll(
                ()->Assertions.assertNotNull(returnedTicket, "Ticket was not found")
                );

    }


    @Test
    @DisplayName("Test findById Not Found")
    void testFindByIdNotFound() {
        doReturn(Optional.empty()).when(port).findTicketByID(1l);

        TicketResponse returnedTicket = delegate.findTicketById(1l);

        Assertions.assertNull(returnedTicket, "Ticket should not be found");
    }



    @Test
    @DisplayName("Test save ticket")
    void testSave() {
        TicketRequest ticket = new TicketRequest();
        ticket.setArrivalTime(LocalTime.of(20,0,0));
        ticket.setArrivalDate(LocalDate.of(2021,6,16));
        ticket.setDepartureTime(LocalTime.of(10,0,0));
        ticket.setDepartureDate(LocalDate.of(2021,6,16));
        ticket.setPassengerAge((short)25);
        ticket.setPassengerName("Pablo Ruiz");
        ticket.setSrcCity("CIUDAD DE MEXICO");
        ticket.setTrgCity("PARIS");

        doReturn(1L).when(port).saveTicket(any(TicketAggregate.class));

        Long id = delegate.saveTicket(ticket);

        Assertions.assertAll(
                ()->Assertions.assertNotNull(id, "The saved ticket should not be null"),
                ()->Assertions.assertSame(id,1L, "The ticket id is not same that request")
        );
    }
}