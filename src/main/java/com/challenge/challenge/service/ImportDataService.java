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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ImportDataService {
    @Autowired
    private ApplicationContext applicationContext;
    public void importAllData() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobLauncher jobLauncher = (JobLauncher) applicationContext.getBean("jobLauncher");

        Job zonesJob = (Job) applicationContext.getBean("importZoneJob");
        Job greenTripJob = (Job) applicationContext.getBean("importGreenTripJob");
        Job yellowTripJob = (Job) applicationContext.getBean("importYellowTripJob");

        jobLauncher.run(zonesJob,new JobParameters());
        jobLauncher.run(greenTripJob,new JobParameters());
        jobLauncher.run(yellowTripJob,new JobParameters());
    }

    public void importZones() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

        JobLauncher jobLauncher = (JobLauncher) applicationContext.getBean("jobLauncher");
        Job zonesJob = (Job) applicationContext.getBean("importZoneJob");
        jobLauncher.run(zonesJob,new JobParameters());
    }

    @Async
    public void importGreenTrip() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

        JobLauncher jobLauncher = (JobLauncher) applicationContext.getBean("jobLauncher");
        Job greenTripJob = (Job) applicationContext.getBean("importGreenTripJob");
        jobLauncher.run(greenTripJob,new JobParameters());
    }

    @Async
    public void importYellowTrip() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

        JobLauncher jobLauncher = (JobLauncher) applicationContext.getBean("jobLauncher");
        Job yellowTripJob = (Job) applicationContext.getBean("importYellowTripJob");
        jobLauncher.run(yellowTripJob,new JobParameters());
    }

}
