package com.flight.management.ticket.infrastructure;

import com.flight.management.ticket.domain.Passenger;
import com.flight.management.ticket.domain.Ticket;
import com.flight.management.ticket.domain.TicketAggregate;
import com.flight.management.ticket.domain.TicketPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Optional;

@Service
public class TicketAdapter  implements TicketPort {

    @Autowired
    PassengerRepository passengerRepository;
    @Autowired
    TicketRepository ticketRepository;

    @Override
    public Optional<TicketAggregate> findTicketByID(Long id) {
        Optional<TicketEntity> ticket = ticketRepository.findById(id);
        if(!ticket.isPresent())
            throw new ResourceNotFoundException(MessageFormat.format("The Ticket {0} does not found",id));

        return Optional.ofNullable(TicketAggregate.builder()
                .ticket(Ticket.builder()
                        .trgCity(ticket.get().getTrgCity())
                        .srcCity(ticket.get().getSrcCity())
                        .departureTime(ticket.get().getDepartureTime())
                        .departureDate(ticket.get().getDepartureDate())
                        .arrivalTime(ticket.get().getArrivalTime())
                        .arrivalDate(ticket.get().getArrivalDate())
                        .id(ticket.get().getId())
                        .build())
                .passenger(Passenger.builder()
                        .age(ticket.get().getPassengerEntity().getAge())
                        .name(ticket.get().getPassengerEntity().getName())
                        .build())
                .build());
    }

    @Override
    public Long saveTicket(TicketAggregate ticketAggregate) {

        PassengerEntity passengerEntity = null;


        Optional<PassengerEntity> opt = passengerRepository.findFirstByNameAndAge(
                ticketAggregate.getPassenger().getName()
                ,ticketAggregate.getPassenger().getAge());

        if (!opt.isPresent()){
             passengerEntity= new PassengerEntity();
            passengerEntity.setAge(ticketAggregate.getPassenger().getAge());
            passengerEntity.setName(ticketAggregate.getPassenger().getName());
            passengerEntity = passengerRepository.save(passengerEntity);
        }else{
            passengerEntity = opt.get();
        }
        TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.setArrivalDate(ticketAggregate.getTicket().getArrivalDate());
        ticketEntity.setArrivalTime(ticketAggregate.getTicket().getArrivalTime());
        ticketEntity.setPassengerEntity(passengerEntity);
        ticketEntity.setDepartureDate(ticketAggregate.getTicket().getDepartureDate());
        ticketEntity.setDepartureTime(ticketAggregate.getTicket().getDepartureTime());
        ticketEntity.setSrcCity(ticketAggregate.getTicket().getSrcCity());
        ticketEntity.setTrgCity(ticketAggregate.getTicket().getTrgCity());

        ticketEntity = ticketRepository.save(ticketEntity);

        return ticketEntity.getId();
    }
}
