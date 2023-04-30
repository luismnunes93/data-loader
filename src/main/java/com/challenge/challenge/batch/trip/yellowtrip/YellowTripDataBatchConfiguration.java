package com.challenge.challenge.batch.trip.yellowtrip;

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
public class YellowTripDataBatchConfiguration {

    @Value("${data.trip.yellow.location}")
    private String zonesCSVLocation;

    @Value("${chunk.trip.size}")
    private Integer chunkSize;

    @Bean
    public FlatFileItemReader<TripCsv> yellowTripReader() {
        return new FlatFileItemReaderBuilder<TripCsv>()
                .name("yellowTripReader")
                .resource(new PathResource(zonesCSVLocation + "yellow_tripdata.csv"))
                .delimited()
                .includedFields(new Integer[] {1,2,7,8})
                .names("pickUpDate", "dropOffDate", "pickUp", "dropOff")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(TripCsv.class);
                }})
                .build();
    }

    @Bean
    public TripItemProcessor yellowTripProcessor() {
        return new TripItemProcessor();
    }

    @Bean
    public Job importYellowTripJob(JobRepository jobRepository,
                                   Step yellowTripStep1) {
        return new JobBuilder("importYellowTripJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(yellowTripStep1)
                .end()
                .build();
    }

    @Bean
    public Step yellowTripStep1(JobRepository jobRepository,
                               PlatformTransactionManager transactionManager, JdbcBatchItemWriter<TripDbFaker> writer) {
        return new StepBuilder("yellowTripStep1", jobRepository)
                .<TripCsv, TripDbFaker> chunk(chunkSize, transactionManager)
                .reader(yellowTripReader())
                .processor(yellowTripProcessor())
                .writer(writer)
                .build();
    }
}
