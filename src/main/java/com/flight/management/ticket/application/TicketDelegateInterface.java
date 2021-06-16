package com.flight.management.ticket.application;

public interface TicketDelegateInterface {

    TicketResponse findTicketById(Long id);
    Long saveTicket(TicketRequest ticket);

}
