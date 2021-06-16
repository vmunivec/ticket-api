package com.flight.management.ticket.application;

import com.flight.management.ticket.domain.Passenger;
import com.flight.management.ticket.domain.Ticket;
import com.flight.management.ticket.domain.TicketAggregate;
import com.flight.management.ticket.domain.TicketPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TicketDelegate implements TicketDelegateInterface {

    @Autowired
    private TicketPort port;

    @Override
    public TicketResponse findTicketById(Long id) {
        Optional<TicketAggregate> aggregate = port.findTicketByID(id);

        TicketResponse ticketResponse = null;

        if (aggregate.isPresent()){
            ticketResponse = TicketResponse.builder()
                            .arrivalDate(aggregate.get().getTicket().getArrivalDate())
                    .arrivalTime(aggregate.get().getTicket().getArrivalTime())
                    .departureDate(aggregate.get().getTicket().getDepartureDate())
                    .departureTime(aggregate.get().getTicket().getDepartureTime())
                    .itineraryID(aggregate.get().getTicket().getId())
                    .passengerAge(aggregate.get().getPassenger().getAge())
                    .passengerName(aggregate.get().getPassenger().getName())
                    .srcCity(aggregate.get().getTicket().getSrcCity())
                    .trgCity(aggregate.get().getTicket().getTrgCity())
                    .build();
        }
        return ticketResponse;
    }

    @Override
    public Long saveTicket(TicketRequest ticket) {

        TicketAggregate aggregate = TicketAggregate.builder()
                .passenger(Passenger.builder()
                        .name(ticket.getPassengerName())
                        .age(ticket.getPassengerAge())
                        .build())
                .ticket(Ticket.builder()
                        .arrivalDate(ticket.getArrivalDate())
                        .arrivalTime(ticket.getArrivalTime())
                        .departureDate(ticket.getDepartureDate())
                        .departureTime(ticket.getDepartureTime())
                        .srcCity(ticket.getSrcCity())
                        .trgCity(ticket.getTrgCity())
                        .build())
                .build();

        return port.saveTicket(aggregate);
    }
}
