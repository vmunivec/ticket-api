package com.flight.management.ticket.infrastructure;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PassengerRepository extends CrudRepository<PassengerEntity, Long> {

    Optional<PassengerEntity> findFirstByNameAndAge(String name, Short age);
}
