package com.challenge.challenge.service;

import com.challenge.challenge.domain.helper.TopZoneTuple;
import com.challenge.challenge.domain.model.Trip;
import com.challenge.challenge.dto.TopZoneDto;
import com.challenge.challenge.dto.TripDto;
import com.challenge.challenge.dto.ZoneTripsDto;
import com.challenge.challenge.mapper.TripMapper;
import com.challenge.challenge.repository.TripRepository;
import com.querydsl.core.types.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Function;

import static com.challenge.challenge.mapper.TopZoneTupleMapper.convertTopZoneTupleToTopZoneDto;
import static com.challenge.challenge.mapper.TopZoneTupleMapper.convertTopZoneTupleToZoneTripsDto;

@Service
@Transactional
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    public Page<TripDto> findAllPageAndFilter(Predicate predicate, Pageable page){
        TripMapper mapper = Mappers.getMapper(TripMapper.class);

        return this.tripRepository.findAll(predicate, page).map(mapper::convertToDto);
    }

    public List<TopZoneDto> getTopByLimit(String order, Integer limit){

        List<TopZoneTuple> zones;
        switch (order){
            case "pickups" -> zones = tripRepository.getTopPickUpByLimit(limit);
            case "dropoffs" -> zones = tripRepository.getTopDropOffByLimit(limit);
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return zones.stream().map(convertTopZoneTupleToTopZoneDto()).toList();
    }

    public ZoneTripsDto sumOfPicksUpsAndDropOffsByZoneAndDate(Integer zone, String date ){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);

        TopZoneTuple zones = tripRepository.getSumOfPicksUpsAndDropOffsByZoneAndDate(zone, localDate, localDate);

        return convertTopZoneTupleToZoneTripsDto(date).apply(zones);
    }
}
