package com.challenge.challenge.batch.yellowtrip;


import com.challenge.challenge.dto.TripDbFaker;
import com.challenge.challenge.dto.YellowTripDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemProcessor;

@Log4j2
public class YellowTripItemProcessor implements ItemProcessor<YellowTripDto, TripDbFaker> {

    @Override
    public TripDbFaker process(final YellowTripDto trip) throws Exception {
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
