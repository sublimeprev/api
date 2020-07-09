package com.api.sublimeprev.model;

import com.api.sublimeprev.domain.RoleEnum;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Consultant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String cpf;
    private String phone;
    private String email;
    private boolean deleted;
    private String password;
    @OneToOne
    private Address address;
    private LocalDateTime dateCreated;
    private LocalDateTime lastUpdated;
    private RoleEnum role;
}
