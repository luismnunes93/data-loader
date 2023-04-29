package com.challenge.challenge.repository;

import com.challenge.challenge.domain.model.Zone;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZoneRepository extends ListCrudRepository<Zone, Long> {
}