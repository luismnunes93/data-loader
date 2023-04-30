package com.challenge.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TripDbFaker {

    private LocalDateTime pickUpDate;
    private LocalDateTime dropOffDate;

    private Integer pickUpId;
    private Integer dropOffId;

}
