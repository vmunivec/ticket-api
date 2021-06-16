package com.flight.management.ticket.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TicketAggregate {

    private Ticket ticket;
    private Passenger passenger;

}
