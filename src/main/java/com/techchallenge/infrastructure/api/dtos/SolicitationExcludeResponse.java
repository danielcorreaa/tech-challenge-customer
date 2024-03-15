package com.techchallenge.infrastructure.api.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record SolicitationExcludeResponse(
        String cpf,
        String name,
        String telephone,
        String city,
        String cep,
        String state,

        String street,
        String number,
        String neighborhood,

        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime dateSolicitation,

        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime dateExclude,
        Boolean executed
) {
}
