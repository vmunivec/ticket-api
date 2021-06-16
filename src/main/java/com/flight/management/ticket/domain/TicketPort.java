package com.flight.management.ticket.domain;

import java.util.Optional;

public interface TicketPort {

    Optional<TicketAggregate> findTicketByID(Long id);
    Long saveTicket(TicketAggregate ticketAggregate);
}
