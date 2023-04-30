package com.challenge.challenge;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaRepositories
@EnableAsync
public class ChallengeApplication {

  public static void main(String[] args) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
    ApplicationContext ctx = SpringApplication.run(ChallengeApplication.class, args);

    JobLauncher jobLauncher = (JobLauncher) ctx.getBean("jobLauncher");

    Job job1 = (Job) ctx.getBean("importZoneJob");
    Job job2 = (Job) ctx.getBean("importGreenTripJob");
    Job job3 = (Job) ctx.getBean("importYellowTripJob");

    // jobLauncher.run(job1,new JobParameters());
    // jobLauncher.run(job2,new JobParameters());
    // jobLauncher.run(job3,new JobParameters());
  }

}
