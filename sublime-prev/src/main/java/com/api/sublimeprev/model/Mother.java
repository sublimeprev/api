package com.api.sublimeprev.model;

import com.api.sublimeprev.domain.MaritalStatusEnum;
import com.api.sublimeprev.domain.RoleEnum;
import com.api.sublimeprev.domain.SchoolingEnum;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Mother{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String cpf;
    private String rg;
    private String pis;
    private String email;
    private MaritalStatusEnum maritalStatusEnum;
    private SchoolingEnum schoolingEnum;
    private String motherName;
    private String fatherName;
    private LocalDate birthday;
    private boolean deleted;
    @OneToOne
    private Address address;
    private LocalDateTime dateCreated;
    private LocalDateTime lastUpdated;
    private RoleEnum role;
}
