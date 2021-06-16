package com.flight.management.ticket.application;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface TicketManagerControllerInterface {

    @RequestMapping(value = "/tickets/{ticket_id}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    default ResponseEntity<TicketResponse> findTicketByID(@PathVariable("ticket_id") Long ticketId) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/tickets",
            produces = { "application/json" },
            method = RequestMethod.POST)
    default ResponseEntity<Void> addTicket(
            @RequestBody TicketRequest ticketRequest) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}
