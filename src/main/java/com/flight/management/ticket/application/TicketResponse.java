package com.flight.management.ticket.application;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder(toBuilder = true)
public class TicketResponse {

    @JsonProperty("itinerary_id")
    private Long itineraryID;
    @JsonProperty("departure_date")
    private LocalDate departureDate;
    @JsonProperty("departure_time")
    private LocalTime departureTime;
    @JsonProperty("arrival_date")
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
