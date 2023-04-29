package com.challenge.challenge.batch.greentrip;

import com.challenge.challenge.dto.GreenTripDto;

import com.challenge.challenge.dto.TripDbFaker;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemProcessor;

@Log4j2
public class GreenTripItemProcessor implements ItemProcessor<GreenTripDto, TripDbFaker> {


    @Override
    public TripDbFaker process(final GreenTripDto trip) throws Exception {
        final String pickUpDate = trip.getPickUpDate();
        final String dropOffDate = trip.getDropOffDate();

        final Integer pickUpId = trip.getPickUpId();
        final Integer dropOffId = trip.getDropOffId();

        final TripDbFaker transformedTrip = new TripDbFaker();
        transformedTrip.setPickUpDate(pickUpDate);
        transformedTrip.setDropOffDate(dropOffDate);
        transformedTrip.setPickUpId(pickUpId);
        transformedTrip.setDropOffId(dropOffId);

        log.info("Converting (" + trip + ") into (" + transformedTrip + ")");

        return transformedTrip;
    }
}
