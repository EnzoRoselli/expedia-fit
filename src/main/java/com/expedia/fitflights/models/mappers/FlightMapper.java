package com.expedia.fitflights.models.mappers;

import com.expedia.fitflights.models.dtos.responses.FlightDTO;
import com.expedia.fitflights.models.entities.Flight;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FlightMapper {
    @Mapping(target = "departure", dateFormat = "hh:mma")
    FlightDTO toFlightDTO(Flight flight);

    List<FlightDTO> toFlightDTOs(List<Flight> flights);
}
