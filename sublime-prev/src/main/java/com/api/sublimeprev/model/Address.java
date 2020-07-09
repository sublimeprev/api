package com.api.sublimeprev.model;

import com.api.sublimeprev.domain.StateEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String street;
    private String neighborhood;
    private String city;
    private String complement;
    private int numberHouse;
    private String zipCode;
    private StateEnum state;
    private LocalDateTime dateCreated;
    private LocalDateTime lastUpdated;
}
