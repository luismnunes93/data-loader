package com.challenge.challenge.service;

import com.challenge.challenge.domain.helper.TopZoneTuple;
import com.challenge.challenge.domain.model.Trip;
import com.challenge.challenge.dto.TopZoneDto;
import com.challenge.challenge.dto.ZoneTripsDto;
import com.challenge.challenge.repository.TripRepository;
import com.querydsl.core.types.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Function;

@Service
@Transactional
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    public Page<Trip> findAllPageAndFilter(Predicate predicate, Pageable page){
        return this.tripRepository.findAll(predicate, page);
    }

    public List<TopZoneDto> getTopByLimit(String order, Integer limit){

        List<TopZoneTuple> zones;
        switch (order){
            case "pickups" -> zones = tripRepository.getTopPickUpByLimit(limit);
            case "dropoffs" -> zones = tripRepository.getTopDropOffByLimit(limit);
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Very Wrong");
        }
        return zones.stream().map(convertTopZoneTupleToTopZoneDto()).toList();
    }

    public List<ZoneTripsDto> sumOfPicksUpsAndDropOffsByZoneAndDate(Integer zone, String date ){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);

        List<TopZoneTuple> zones = tripRepository.getSumOfPicksUpsAndDropOffsByZoneAndDate(zone, localDate, localDate);

        return zones.stream().map(convertTopZoneTupleToZoneTripsDto(date)).toList();
    }

    private static Function<TopZoneTuple, ZoneTripsDto> convertTopZoneTupleToZoneTripsDto(String date) {
        return z -> {
            ZoneTripsDto zoneTripsDto = new ZoneTripsDto();
            zoneTripsDto.setZone(z.getZone());
            zoneTripsDto.setDate(date);
            zoneTripsDto.setPu_total(z.getPu_total());
            zoneTripsDto.setDo_total(z.getDo_total());
            return zoneTripsDto;
        };
    }

    private static Function<TopZoneTuple, TopZoneDto> convertTopZoneTupleToTopZoneDto() {
        return z -> {
            TopZoneDto topZoneDto = new TopZoneDto();
            topZoneDto.setZone(z.getZone());
            topZoneDto.setPu_total(z.getPu_total());
            topZoneDto.setDo_total(z.getDo_total());
            return topZoneDto;
        };
    }
}
