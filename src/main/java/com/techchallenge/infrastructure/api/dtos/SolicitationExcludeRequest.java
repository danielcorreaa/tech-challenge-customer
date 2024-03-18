package com.techchallenge.infrastructure.api.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

public record SolicitationExcludeRequest(

        @NotBlank(message = "Cpf is required!")
        @Size(max = 11, min = 11,  message = "Cpf without correct number of digits!")
        @Getter
        String cpf,
        @NotBlank(message = "Name is required!")
        @Getter
        String name,
        @NotBlank(message = "Telephone is required!")
        @Getter
        String telephone,

        @NotBlank(message = "City is required!")
        @Getter
        String city,

        @NotBlank(message = "Cep is required!")
        @Getter
        String cep,

        @NotBlank(message = "State is required!")
        @Getter
        String state,

        @NotBlank(message = "Street is required!")
        @Getter
        String street,

        @NotBlank(message = "Number is required!")
        @Getter
        String number,

        @NotBlank(message = "Neighborhood is required!")
        @Getter
        String neighborhood

        
) {
}
