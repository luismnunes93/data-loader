package com.challenge.challenge.config;

import com.challenge.challenge.batch.greentrip.GreenTripItemProcessor;
import com.challenge.challenge.dto.GreenTripDto;
import com.challenge.challenge.dto.TripDbFaker;
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

    @Value("${tripdata.green.location}")
    private String zonesCSVLocation;

    @Bean
    public FlatFileItemReader<GreenTripDto> greenTripReader() {
        return new FlatFileItemReaderBuilder<GreenTripDto>()
                .name("greenTripReader")
                .resource(new PathResource(zonesCSVLocation + "green_tripdata_2018-01_01-15.csv"))
                .delimited()
                .names("a", "pickUpDate", "dropOffDate", "b", "c", "pickUp", "dropOff",
                        "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<GreenTripDto>() {{
                    setTargetType(GreenTripDto.class);
                }})
                .build();
    }

    @Bean
    public GreenTripItemProcessor greenTripProcessor() {
        return new GreenTripItemProcessor();
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
                .<GreenTripDto, TripDbFaker> chunk(10, transactionManager)
                .reader(greenTripReader())
                .processor(greenTripProcessor())
                .writer(writer)
                .build();
    }
}
