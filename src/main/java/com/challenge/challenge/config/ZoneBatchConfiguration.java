package com.challenge.challenge.config;

import com.challenge.challenge.batch.zone.ZoneJobCompletionNotificationListener;
import com.challenge.challenge.domain.model.Zone;
import com.challenge.challenge.batch.zone.ZoneItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class ZoneBatchConfiguration {

    @Value("${zones.location}")
    private String zonesCSVLocation;

    @Bean
    public FlatFileItemReader<Zone> zoneReader() {
        return new FlatFileItemReaderBuilder<Zone>()
                .name("zoneReader")
                .resource(new PathResource(zonesCSVLocation + "zones.csv"))
                .delimited()
                .names("id", "borough", "zone", "serviceZone")
                .linesToSkip(1)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Zone>() {{
                    setTargetType(Zone.class);
                }})
                .build();
    }

    @Bean
    public ZoneItemProcessor zoneProcessor() {
        return new ZoneItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Zone> zoneWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Zone>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO Zone (id, borough, zone, service_zone) VALUES (:id, :borough, :zone, :serviceZone)")
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public Job importZoneJob(JobRepository jobRepository,
                             ZoneJobCompletionNotificationListener listener, Step zoneStep1) {
        return new JobBuilder("importZoneJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(zoneStep1)
                .end()
                .build();
    }

    @Bean
    public Step zoneStep1(JobRepository jobRepository,
                          PlatformTransactionManager transactionManager, JdbcBatchItemWriter<Zone> writer) {
        return new StepBuilder("zoneStep1", jobRepository)
                .<Zone, Zone> chunk(10, transactionManager)
                .reader(zoneReader())
                .processor(zoneProcessor())
                .writer(writer)
                .build();
    }
}
