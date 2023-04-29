package com.challenge.challenge.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TripDto {

    private Long id;

    private LocalDateTime pickUpDate;
    private LocalDateTime dropOffDate;

    private ZoneDto pickUp;
    private ZoneDto dropOff;
}