package com.challenge.challenge.batch.trip.yellowtrip;


import com.challenge.challenge.dto.TripDbFaker;
import com.challenge.challenge.dto.YellowTripDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Log4j2
public class YellowTripItemProcessor implements ItemProcessor<YellowTripDto, TripDbFaker> {

    @Override
    public TripDbFaker process(final YellowTripDto trip) throws Exception {

        final TripDbFaker transformedTrip = new TripDbFaker();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime pickUpDate = LocalDateTime.parse(trip.getPickUpDate(), formatter);
        LocalDateTime dropOffDate = LocalDateTime.parse(trip.getDropOffDate(), formatter);

        transformedTrip.setPickUpDate(pickUpDate);
        transformedTrip.setDropOffDate(dropOffDate);

        transformedTrip.setPickUpId(trip.getPickUpId());
        transformedTrip.setDropOffId(trip.getDropOffId());

        log.info("Converting (" + trip + ") into (" + transformedTrip + ")");

        return transformedTrip;
    }
}
