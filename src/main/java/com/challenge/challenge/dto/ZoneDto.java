package com.challenge.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ZoneDto {

    private Integer id;
    private String borough;
    private String zone;
    private String serviceZone;
}