package com.expedia.fitflights.repositories;

import com.expedia.fitflights.models.entities.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    /*
    * The time we receive from the client must be less tan or equal to 5 hours
    * with respect to the flight departure time.
    * */
    @Query(value = "SELECT * FROM flights f\n" +
                    "WHERE (ABS(HOUR(departure) - ?1) <= 4)\n" +
                    "OR (ABS(HOUR(departure) - ?1) <= 5) and (MINUTE(departure) - ?2) >= 0",
            nativeQuery = true)
    List<Flight> findFlightByDeparture_HourAndDeparture_Minute(Integer hour, Integer minutes);
}
