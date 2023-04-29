package com.challenge.challenge.domain.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Trip {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime pickUpDate;
    private LocalDateTime dropOffDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Zone pickUp;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Zone dropOff;
}
