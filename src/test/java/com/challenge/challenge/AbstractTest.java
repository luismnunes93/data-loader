package com.challenge.challenge;

import com.challenge.challenge.repository.ZoneRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("unit-tests")
@SpringBootTest
public class AbstractTest {

    @Autowired
    private ZoneRepository zoneRepository;


    @AfterEach
    public void tearDown(){
        this.zoneRepository.deleteAll();
    }
}
