package com.challenge.challenge.batch.zone;

import com.challenge.challenge.domain.model.Zone;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemProcessor;

@Log4j2
public class ZoneItemProcessor implements ItemProcessor<Zone, Zone> {


    @Override
    public Zone process(final Zone zoneInput) throws Exception {
        final Integer id = zoneInput.getId();
        final String borough = zoneInput.getBorough();
        final String zone = zoneInput.getZone();
        final String serviceZone = zoneInput.getServiceZone();

        final Zone transformedZone = new Zone(id, borough, zone, serviceZone);

        log.info("Converting (" + zoneInput + ") into (" + transformedZone + ")");

        return transformedZone;
    }

}
