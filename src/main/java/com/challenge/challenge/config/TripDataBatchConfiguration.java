package com.challenge.challenge.config;

import com.challenge.challenge.dto.TripDbFaker;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class TripDataBatchConfiguration {
    @Bean
    public JdbcBatchItemWriter<TripDbFaker> tripWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<TripDbFaker>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO Trip (pick_up_date, drop_off_date, pick_up_id, drop_off_id) " +
                        "VALUES (:pickUpDate, :dropOffDate, :pickUpId, :dropOffId)")
                .dataSource(dataSource)
                .build();
    }
}
