package com.challenge.challenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ZoneTripsDto {

    private String zone;
    private String date;

    @JsonProperty("pu")
    private String pu_total;
    @JsonProperty("do")
    private String do_total;
}
