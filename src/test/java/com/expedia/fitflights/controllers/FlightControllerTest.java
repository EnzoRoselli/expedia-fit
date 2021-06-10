package com.expedia.fitflights.controllers;

import com.expedia.fitflights.models.dtos.responses.FlightListDTO;
import com.expedia.fitflights.models.entities.Flight;
import com.expedia.fitflights.models.enums.TimeOfDay;
import com.expedia.fitflights.services.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.MockitoAnnotations.openMocks;

public class FlightControllerTest {

    MockMvc mockMvc;
    @Mock
    private FlightService flightService;
    @InjectMocks
    private FlightController flightController;

    Flight flight1, flight2, flight3, flight4;
    List<Flight> flights;
    FlightListDTO flightListDTO;

    @BeforeEach
    public void setUp(){
        openMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(flightController)
                    .setControllerAdvice(new AdviceController())
                    .build();

        LocalDate localDate = LocalDate.of(2021, Month.JANUARY, 1);
        flight1 = Flight.builder().id(1L).flight("Air Canada 8099").departure(LocalDateTime.of(localDate, LocalTime.of(7, 30))).build();
        flight2 = Flight.builder().id(2L).flight("United Airline 6115").departure(LocalDateTime.of(localDate, LocalTime.of(10, 30))).build();
        flight3 = Flight.builder().id(3L).flight("WestJet 6456").departure(LocalDateTime.of(localDate, LocalTime.of(12, 30))).build();
        flight4 = Flight.builder().id(4L).flight("Delta 3833").departure(LocalDateTime.of(localDate, LocalTime.of(15, 0))).build();

        flights = new ArrayList<>();
        flightListDTO = new FlightListDTO();
    }

    @Test
    public void getFlightsByDepartureTime_ExpectedValues_Ok(){
        flights.addAll(Arrays.asList(flight3, flight4));
//        flightListDTO.setFlights(FlightDTO.fromFlightsToFlightDTOs(flights));

//        when(flightService.getFlightsByDepartureTime(5, 30, TimeOfDay.PM)).thenReturn(flightListDTO);
        assertEquals(flightController.getFlightsByDepartureTime(5, 30, TimeOfDay.PM), flightListDTO);
    }
}
