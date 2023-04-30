package com.challenge.challenge.mapper;

import com.challenge.challenge.domain.helper.TopZoneTuple;
import com.challenge.challenge.dto.TopZoneDto;
import com.challenge.challenge.dto.ZoneTripsDto;

import java.util.function.Function;

public class TopZoneTupleMapper {

    public static Function<TopZoneTuple, ZoneTripsDto> convertTopZoneTupleToZoneTripsDto(String date) {
        return z -> {
            ZoneTripsDto zoneTripsDto = new ZoneTripsDto();
            zoneTripsDto.setZone(z.getZone());
            zoneTripsDto.setDate(date);
            zoneTripsDto.setPu_total(z.getPu_total());
            zoneTripsDto.setDo_total(z.getDo_total());
            return zoneTripsDto;
        };
    }

    public static Function<TopZoneTuple, TopZoneDto> convertTopZoneTupleToTopZoneDto() {
        return z -> {
            TopZoneDto topZoneDto = new TopZoneDto();
            topZoneDto.setZone(z.getZone());
            topZoneDto.setPu_total(z.getPu_total());
            topZoneDto.setDo_total(z.getDo_total());
            return topZoneDto;
        };
    }
}
