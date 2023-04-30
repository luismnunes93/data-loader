package com.challenge.challenge.config;

import com.challenge.challenge.batch.trip.yellowtrip.YellowTripItemProcessor;
import com.challenge.challenge.dto.TripDbFaker;
import com.challenge.challenge.dto.YellowTripDto;
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
    public FlatFileItemReader<YellowTripDto> yellowTripReader() {
        return new FlatFileItemReaderBuilder<YellowTripDto>()
                .name("yellowTripReader")
                .resource(new PathResource(zonesCSVLocation + "yellow_tripdata_2018-01_01-15.csv"))
                .delimited()
                .names("a", "pickUpDate", "dropOffDate", "b", "c", "d", "e", "pickUp", "dropOff",
                        "f", "g", "h", "i", "j", "k", "l", "m")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(YellowTripDto.class);
                }})
                .build();
    }

    @Bean
    public YellowTripItemProcessor yellowTripProcessor() {
        return new YellowTripItemProcessor();
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
                .<YellowTripDto, TripDbFaker> chunk(chunkSize, transactionManager)
                .reader(yellowTripReader())
                .processor(yellowTripProcessor())
                .writer(writer)
                .build();
    }
}
