package com.challenge.challenge.mapper;

import com.challenge.challenge.domain.model.Trip;
import com.challenge.challenge.dto.TripDto;
import org.mapstruct.Mapper;

@Mapper
public interface TripMapper {

    TripDto convertToDto(Trip topZone);
}
