package com.expedia.fitflights.services;

import com.expedia.fitflights.models.dtos.responses.FlightListDTO;
import com.expedia.fitflights.models.entities.Flight;
import com.expedia.fitflights.models.enums.TimeOfDay;
import com.expedia.fitflights.models.mappers.FlightMapper;
import com.expedia.fitflights.repositories.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository repo;
    private final FlightMapper flightMapper;

    public FlightListDTO getFlightsByDepartureTime(Integer hour, Integer minutes, TimeOfDay time){
        List<Flight> flights = repo.findFlightByDeparture_HourAndDeparture_Minute(checkHourUponTimeOfDay(hour, time), minutes);

        return new FlightListDTO(flightMapper.toFlightDTOs(flights));
    }

    private Integer checkHourUponTimeOfDay(Integer hour, TimeOfDay time){
        return (time.name().equalsIgnoreCase(TimeOfDay.PM.toString())) ? (hour + 12) : hour;
    }
}
