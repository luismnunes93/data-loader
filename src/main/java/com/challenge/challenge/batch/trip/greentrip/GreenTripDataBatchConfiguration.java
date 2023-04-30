package com.challenge.challenge.batch.trip.greentrip;

import com.challenge.challenge.batch.trip.TripItemProcessor;
import com.challenge.challenge.domain.csv.TripCsv;
import com.challenge.challenge.domain.helper.TripDbFaker;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;
import org.springframework.transaction.PlatformTransactionManager;


@Configuration
public class GreenTripDataBatchConfiguration {

    @Value("${data.trip.green.location}")
    private String zonesCSVLocation;

    @Value("${chunk.trip.size}")
    private Integer chunkSize;

    @Bean
    public FlatFileItemReader<TripCsv> greenTripReader() {
        return new FlatFileItemReaderBuilder<TripCsv>()
                .name("greenTripReader")
                .resource(new PathResource(zonesCSVLocation + "green_tripdata.csv"))
                .delimited()
                .includedFields(new Integer[] {1,2,5,6})
                .names("pickUpDate", "dropOffDate", "pickUp", "dropOff")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<TripCsv>() {{
                    setTargetType(TripCsv.class);
                }})
                .build();
    }

    @Bean
    public TripItemProcessor greenTripProcessor() {
        return new TripItemProcessor();
    }

    @Bean
    public Job importGreenTripJob(JobRepository jobRepository, Step greenTripStep1) {
        return new JobBuilder("importGreenTripJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(greenTripStep1)
                .end()
                .build();
    }

    @Bean
    public Step greenTripStep1(JobRepository jobRepository,
                               PlatformTransactionManager transactionManager, JdbcBatchItemWriter<TripDbFaker> writer) {
        return new StepBuilder("greenTripStep1", jobRepository)
                .<TripCsv, TripDbFaker> chunk(chunkSize, transactionManager)
                .reader(greenTripReader())
                .processor(greenTripProcessor())
                .writer(writer)
                .build();
    }
}
