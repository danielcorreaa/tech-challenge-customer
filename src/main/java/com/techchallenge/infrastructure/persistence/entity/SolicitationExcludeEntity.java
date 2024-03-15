package com.techchallenge.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "cpf")
@Entity
@Table(name = "solicitation-exclude")
public class SolicitationExcludeEntity {

    @Id
    @Column(name = "cpf", nullable = false)
    private String cpf;
    private String nome;
    private String telephone;
    @Embedded
    private AddressEntity address;
    private LocalDateTime dateSolicitation;
    private LocalDateTime dateExclude;
    private Boolean executed;
}
