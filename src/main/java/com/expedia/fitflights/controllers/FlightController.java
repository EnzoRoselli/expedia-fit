package com.expedia.fitflights.controllers;

import com.expedia.fitflights.models.dtos.responses.FlightListDTO;
import com.expedia.fitflights.models.enums.TimeOfDay;
import com.expedia.fitflights.services.FlightService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


@RestController
@RequestMapping("/flights")
@RequiredArgsConstructor
@Validated
public class FlightController {

    private final FlightService flightService;

    @GetMapping
    @ApiOperation(value = "Find flights by departure time",
            notes = "Provide an hour, minutes, and time period to look up for flights in a range of 5 hours of the indicated time")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Ok"), @ApiResponse(code = 400, message = "Bad Request")})
    public FlightListDTO getFlightsByDepartureTime(@RequestParam @Min(1) @Max(12) Integer hour,
                                                   @RequestParam(required = false, defaultValue = "0") @Min(0) @Max(59) Integer minutes,
                                                   @RequestParam(required = false, defaultValue = "AM") TimeOfDay time){
        return flightService.getFlightsByDepartureTime(hour, minutes, time);
    }
}
