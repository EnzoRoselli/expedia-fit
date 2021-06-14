package com.expedia.fitflights.services;

import com.expedia.fitflights.models.dtos.responses.FlightDTO;
import com.expedia.fitflights.models.dtos.responses.FlightListDTO;
import com.expedia.fitflights.models.entities.Flight;
import com.expedia.fitflights.models.enums.TimeOfDay;
import com.expedia.fitflights.models.mappers.FlightMapper;
import com.expedia.fitflights.repositories.FlightRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class FlightServiceTest {

    @Mock
    private FlightRepository flightRepository;
    @Mock
    private FlightMapper flightMapper;
    @InjectMocks
    private FlightService flightService;

    @Test
    void getFlightsByDepartureTime_ExpectedValues_Ok() {
        //given
        List<Flight> flights = new ArrayList<>();
        List<FlightDTO> flightDTOs = Arrays.asList(new FlightDTO(), new FlightDTO());
        given(flightRepository.findFlightByDeparture_HourAndDeparture_Minute(anyInt(),anyInt())).willReturn(flights);
        given(flightMapper.toFlightDTOs(any(List.class))).willReturn(flightDTOs);

        //when
        FlightListDTO flightList = flightService.getFlightsByDepartureTime(6, 30, TimeOfDay.AM);

        //then
        then(flightRepository).should().findFlightByDeparture_HourAndDeparture_Minute(anyInt(), anyInt());
        then(flightMapper).should().toFlightDTOs(flights);
        assertThat(flightList).isNotNull();
        assertThat(flightList.getFlights()).hasSize(2);
    }
}