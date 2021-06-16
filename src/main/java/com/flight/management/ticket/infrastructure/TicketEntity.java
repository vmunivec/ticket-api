package com.flight.management.ticket.infrastructure;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "ticket")
public class TicketEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "departure_date")
    private LocalDate departureDate;
    @Column(name = "departure_time")
    private LocalTime departureTime;
    @Column(name = "arrival_date")
    private LocalDate arrivalDate;
    @Column(name = "arrival_time")
    private LocalTime arrivalTime;
    @Column(name = "src_city")
    private String srcCity;
    @Column(name = "trg_city")
    private String trgCity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "passenger_id", referencedColumnName = "id")
    private PassengerEntity passengerEntity;

}
