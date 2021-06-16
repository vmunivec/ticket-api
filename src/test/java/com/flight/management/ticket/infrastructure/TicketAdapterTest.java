package com.flight.management.ticket.infrastructure;

import com.flight.management.ticket.domain.Passenger;
import com.flight.management.ticket.domain.Ticket;
import com.flight.management.ticket.domain.TicketAggregate;
import org.junit.jupiter.api.*;
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
public class TicketAdapterTest {

    @InjectMocks
    private TicketAdapter adapter;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private PassengerRepository passengerRepository;

    TicketEntity ticketEntity = new TicketEntity();

    PassengerEntity passengerEntity = new PassengerEntity();

    @BeforeEach
    void intit(){
        ticketEntity.setId(1L);
        ticketEntity.setArrivalDate(LocalDate.of(2021,6,16));
        ticketEntity.setArrivalTime(LocalTime.of(20,0,0));
        ticketEntity.setDepartureDate(LocalDate.of(2021,6,16));
        ticketEntity.setDepartureTime(LocalTime.of(10,0,0));
        ticketEntity.setSrcCity("CIUDAD DE MEXICO");
        ticketEntity.setTrgCity("PARIS");

        passengerEntity.setId(1L);
        passengerEntity.setAge((short)25);
        passengerEntity.setName("Pablo Ruiz");
        ticketEntity.setPassengerEntity(passengerEntity);
    }

    @Test
    @DisplayName("Test findById Success")
    void testFindById() {

        doReturn(Optional.of(ticketEntity)).when(ticketRepository).findById(1l);
        Optional<TicketAggregate> returnedTicket = adapter.findTicketByID(1l);
        Assertions.assertAll(
                ()->Assertions.assertTrue(returnedTicket.isPresent(), "Ticket was not found"),
                ()->Assertions.assertNotNull(returnedTicket.get(), "Ticket ticket should not be null")
                );
    }


    @Test
    @DisplayName("Test findById Not Found")
    void testFindByIdNotFound() {
        doReturn(Optional.empty()).when(ticketRepository).findById(1l);

        ResourceNotFoundException exception = Assertions.assertThrows(
                ResourceNotFoundException.class,
                ()->adapter.findTicketByID(1l),
                "Expected adapter.findTicketByID(1l) to thow, but it didn't"
        );

        Assertions.assertAll(
                ()->Assertions.assertNotNull(exception),
                ()->Assertions.assertNotNull(exception.getMessage())
        );
    }



    @Test
    @DisplayName("Test save ticket when the passenger is not present")
    void testSaveTicketWhenPassengerIsNotPresent() {
        TicketAggregate ticketAggregate = TicketAggregate.builder()
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

        doReturn(ticketEntity).when(ticketRepository).save(any(TicketEntity.class));

        Long id = adapter.saveTicket(ticketAggregate);

        Assertions.assertAll(
                ()->Assertions.assertNotNull(id, "The saved ticket should not be null"),
                ()->Assertions.assertSame(id,ticketEntity.getId(), "The ticket id is not same that request")
        );
    }

    @Test
    @DisplayName("Test save ticket when the passenger is present")
    void testSaveTicketWhenPassengerIsPresent() {
        TicketAggregate ticketAggregate = TicketAggregate.builder()
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

        doReturn(Optional.of(passengerEntity)).when(passengerRepository).findFirstByNameAndAge(any(String.class),any(Short.class));

        doReturn(ticketEntity).when(ticketRepository).save(any(TicketEntity.class));

        Long id = adapter.saveTicket(ticketAggregate);

        Assertions.assertAll(
                ()->Assertions.assertNotNull(id, "The saved ticket should not be null"),
                ()->Assertions.assertSame(id,ticketEntity.getId(), "The ticket id is not same that request")
        );
    }
}