package com.challenge.challenge.controller;

import com.challenge.challenge.domain.model.Trip;
import com.challenge.challenge.dto.ZoneTripsDto;
import com.challenge.challenge.dto.TripDto;
import com.challenge.challenge.mapper.TripMapper;
import com.challenge.challenge.service.TripService;
import com.querydsl.core.types.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Transactional
@RequestMapping("/api")
public class ZoneController {

    private final TripService tripService;

    @GetMapping("/top-zones")
    public ResponseEntity<Object> topZones(@RequestParam String order){

        return new ResponseEntity<>(this.tripService.getTopByLimit(order, 5) ,HttpStatus.OK);
    }

    @GetMapping("/zone-trips")
    public ResponseEntity<List<ZoneTripsDto>> sumOfPicksUpsAndDropOffsByZoneAndDate(@RequestParam Integer zone,
                                                                                    @RequestParam String date){

        return new ResponseEntity<>(this.tripService.sumOfPicksUpsAndDropOffsByZoneAndDate(zone, date),
                HttpStatus.OK);
    }

    @GetMapping("/list-yellow")
    public ResponseEntity<Page<TripDto>> findPageAndFilter(@QuerydslPredicate(root = Trip.class) Predicate predicate,
                                                        Pageable page){

        TripMapper mapper = Mappers.getMapper(TripMapper.class);

        return new ResponseEntity<>(this.tripService.findAllPageAndFilter(predicate, page).map(mapper::convertToDto),
                HttpStatus.OK);
    }
}
