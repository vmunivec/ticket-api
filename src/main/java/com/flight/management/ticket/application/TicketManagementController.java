package com.flight.management.ticket.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class TicketManagementController implements TicketManagerControllerInterface{

   @Autowired
    private TicketDelegateInterface delegate;

    @Override
    public ResponseEntity<TicketResponse> findTicketByID(Long ticketId) {
        return ResponseEntity.ok().body(delegate.findTicketById(ticketId));
    }

    @Override
    public ResponseEntity<Void> addTicket(TicketRequest ticketRequest) {
        final long ticketId = delegate.saveTicket(ticketRequest);
        final URI location = ServletUriComponentsBuilder
                .fromCurrentServletMapping().path("/tickets/{ticket_id}").build()
                .expand(ticketId).toUri();
        return ResponseEntity.created(location).build();
    }
}
