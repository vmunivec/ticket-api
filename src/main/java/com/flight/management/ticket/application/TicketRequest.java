package com.flight.management.ticket.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class TicketRequest {

    @JsonProperty("departure_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate departureDate;
    @JsonProperty("departure_time")
    private LocalTime departureTime;
    @JsonProperty("arrival_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate arrivalDate;
    @JsonProperty("arrival_time")
    private LocalTime arrivalTime;
    @JsonProperty("source_city")
    private String srcCity;
    @JsonProperty("target_city")
    private String trgCity;
    @JsonProperty("passenger_name")
    private String passengerName;
    @JsonProperty("passenger_age")
    private Short passengerAge;


}
