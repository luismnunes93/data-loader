package com.challenge.challenge.service;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class ImportDataService {
    @Autowired
    private ApplicationContext applicationContext;
    public void importAllData() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobLauncher jobLauncher = (JobLauncher) applicationContext.getBean("jobLauncher");

        Job job1 = (Job) applicationContext.getBean("importZoneJob");
        Job job2 = (Job) applicationContext.getBean("importGreenTripJob");
        Job job3 = (Job) applicationContext.getBean("importYellowTripJob");

        jobLauncher.run(job1,new JobParameters());
        jobLauncher.run(job2,new JobParameters());
        jobLauncher.run(job3,new JobParameters());
    }
}
