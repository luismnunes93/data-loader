package com.challenge.challenge.domain.csv;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TripCsv {

    private String pickUpDate;
    private String dropOffDate;

    private Integer pickUpId;
    private Integer dropOffId;

}