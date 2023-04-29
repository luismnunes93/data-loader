package com.challenge.challenge.repository;

import com.challenge.challenge.domain.helper.TopZoneTuple;
import com.challenge.challenge.domain.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long>, QuerydslPredicateExecutor<Trip> {
    @Query(value = """
            SELECT zone, pu_total, do_total
            FROM\s
                (SELECT pick_up_id, count(id) as pu_total\s
            \tFROM Trip
            \tGROUP BY pick_up_id) pu
            INNER JOIN
                (SELECT drop_off_id, count(id) as do_total,\s
            \tFROM Trip
            \tGROUP BY drop_off_id) do
            ON (pu.pick_up_id = do.drop_off_id)
            INNER JOIN Zone z
            ON (pu.pick_up_id = z.id)
            ORDER BY pu_total DESC LIMIT :limit""",
            nativeQuery = true)
    List<TopZoneTuple> getTopPickUpByLimit(@Param("limit") Integer limit);

    @Query(value = """
            SELECT zone, pu_total, do_total
            FROM\s
                (SELECT pick_up_id, count(id) as pu_total\s
            \tFROM Trip
            \tGROUP BY pick_up_id) pu
            INNER JOIN
                (SELECT drop_off_id, count(id) as do_total,\s
            \tFROM Trip
            \tGROUP BY drop_off_id) do
            ON (pu.pick_up_id = do.drop_off_id)
            INNER JOIN Zone z
            ON (pu.pick_up_id = z.id)
            ORDER BY do_total DESC LIMIT ?1""", nativeQuery = true)
    List<TopZoneTuple> getTopDropOffByLimit(Integer limit);

    @Query(value = """
            SELECT zone, pu_total, do_total
            FROM\s
                (SELECT pick_up_id, count(pick_up_id) as pu_total\s
            FROM Trip
            WHERE pick_up_date BETWEEN :beginDate AND :endDate\s
            AND pick_up_id = :zone\s
            GROUP BY pick_up_id) pu
            INNER JOIN
                (SELECT drop_off_id, count(drop_off_id) as do_total
            FROM Trip
            WHERE drop_off_date BETWEEN :beginDate AND :endDate\s
            AND drop_off_id = :zone\s
            GROUP BY drop_off_id) do
            ON (pu.pick_up_id = do.drop_off_id)\s
            INNER JOIN Zone z
            ON (pu.pick_up_id = z.id)
            """, nativeQuery = true)
    List<TopZoneTuple> getSumOfPicksUpsAndDropOffsByZoneAndDate(@Param("zone") Integer zone,
                                                                @Param("beginDate") String beginDate,
                                                                @Param("endDate") String endDate);
}