package com.flight.management.ticket.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Passenger {

    private String name;
    private Short age;


}
