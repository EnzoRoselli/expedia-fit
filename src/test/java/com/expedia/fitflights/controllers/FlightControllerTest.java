package com.expedia.fitflights.controllers;

import com.expedia.fitflights.models.dtos.responses.FlightDTO;
import com.expedia.fitflights.models.dtos.responses.FlightListDTO;
import com.expedia.fitflights.models.entities.Flight;
import com.expedia.fitflights.models.enums.TimeOfDay;
import com.expedia.fitflights.services.FlightService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class FlightControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Mock
    private FlightService flightService;
    @InjectMocks
    private FlightController flightController;

    private JacksonTester<FlightListDTO> jsonFlightListDTO;


    Flight flight1, flight2;
    FlightListDTO flightListDTO;

    @BeforeEach
    public void setUp(){

        mockMvc = MockMvcBuilders.standaloneSetup(flightController)
                    .setControllerAdvice(new AdviceController())
                    .build();

        JacksonTester.initFields(this, new ObjectMapper());

        LocalDate localDate = LocalDate.of(2021, Month.JANUARY, 1);
        flight1 = Flight.builder().id(1L).flight("Air Canada 8099").departure(LocalDateTime.of(localDate, LocalTime.of(7, 30))).build();
        flight2 = Flight.builder().id(2L).flight("United Airline 6115").departure(LocalDateTime.of(localDate, LocalTime.of(10, 30))).build();

        flightListDTO = FlightListDTO.builder()
                .flights(Stream.of(flight1, flight2)
                .map(f -> new FlightDTO(f.getFlight(), f.getDeparture().format(DateTimeFormatter.ofPattern("hh:mma"))))
                .collect(Collectors.toList())).build();
    }

    @Test
    public void getFlightsByDepartureTime_ExpectedValues_Ok() throws Exception {
        //given
        given(flightService.getFlightsByDepartureTime(anyInt(), anyInt(), any(TimeOfDay.class))).willReturn(flightListDTO);

        //when
        MockHttpServletResponse response = mockMvc.perform(get("/flights/?hour=5&minutes=00&time=am")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        assertThat(response.getContentAsString()).isEqualTo(jsonFlightListDTO.write(flightListDTO).getJson());
    }

    @Test
    public void getFlightsByDepartureTime_HourLessThanOne_ConstraintViolationException() throws Exception {
        //given
        given(flightController.getFlightsByDepartureTime(anyInt(), anyInt(), any(TimeOfDay.class))).willThrow(ConstraintViolationException.class);

        //when
        MockHttpServletResponse response = mockMvc.perform(get("/flights/?hour=0&minutes=00&time=am")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void getFlightsByDepartureTime_HourMoreThanTwelve_ConstraintViolationException() throws Exception {
        //given
        given(flightController.getFlightsByDepartureTime(anyInt(), anyInt(), any(TimeOfDay.class))).willThrow(ConstraintViolationException.class);

        //when
        MockHttpServletResponse response = mockMvc.perform(get("/flights/?hour=15&minutes=00&time=am")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void getFlightsByDepartureTime_InvalidTimeOfDay_MethodArgumentTypeMismatchException() throws Exception {
        //given
        given(flightController.getFlightsByDepartureTime(anyInt(), anyInt(), any(TimeOfDay.class))).willThrow(MethodArgumentTypeMismatchException.class);

        //when
        MockHttpServletResponse response = mockMvc.perform(get("/flights/?hour=10&minutes=00")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}
