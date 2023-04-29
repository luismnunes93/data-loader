package com.challenge.challenge.scheduler;

import com.challenge.challenge.service.ImportDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImportDataScheduler {

    private final ImportDataService importDataService;

    @Scheduled(cron="0 0 1 1 1/1 *")
    void importData() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        importDataService.importAllData();
    }
}
