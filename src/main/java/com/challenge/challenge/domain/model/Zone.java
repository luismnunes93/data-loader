package com.challenge.challenge.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Zone {

    @Id
    private Integer id;

    private String borough;
    private String zone;
    private String serviceZone;
}
