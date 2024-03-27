package com.techchallenge.infrastructure.persistence.entity;

import jakarta.persistence.Embeddable;
import lombok.*;
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class AddressEntity {

    private String city;
    private String cep;
    private String state;

    private String street;
    private String number;
    private String neighborhood;

}
