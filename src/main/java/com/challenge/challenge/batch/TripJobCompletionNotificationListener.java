package com.challenge.challenge.batch;

import com.challenge.challenge.dto.TripDbFaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class TripJobCompletionNotificationListener implements JobExecutionListener {


    private final JdbcTemplate jdbcTemplate;

    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");

            jdbcTemplate.query("SELECT pick_up_date, drop_off_date, pick_up_id, drop_off_id FROM trip",
                    (rs, row) -> new TripDbFaker(
                            rs.getString(1),
                            rs.getString(2),
                            rs.getInt(3),
                            rs.getInt(4))
            ).forEach(person -> log.info("Found <{{}}> in the database.", person));
        }
    }
}
