package com.challenge.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TripDbFaker {

    private String pickUpDate;
    private String dropOffDate;

    private Integer pickUpId;
    private Integer dropOffId;

}
