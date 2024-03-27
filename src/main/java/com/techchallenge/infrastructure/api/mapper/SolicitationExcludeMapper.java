package com.techchallenge.infrastructure.api.mapper;

import com.techchallenge.core.response.Result;
import com.techchallenge.domain.entity.SolicitationExclude;
import com.techchallenge.domain.valueobject.Address;
import com.techchallenge.infrastructure.api.dtos.SolicitationExcludeRequest;
import com.techchallenge.infrastructure.api.dtos.SolicitationExcludeResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SolicitationExcludeMapper {
    public SolicitationExclude toSolicitateExclude(SolicitationExcludeRequest request) {
        return new SolicitationExclude(request.cpf(), request.name(), request.telephone(), getEndereco(request));
    }

    private Address getEndereco(SolicitationExcludeRequest request) {
        return Address.EnderecoBuilder.anEndereco()
                .withStreet(request.street())
                .withState(request.state())
                .withNumber(request.number())
                .withNeighborhood(request.neighborhood())
                .withCep(request.cep())
                .withCity(request.city())
                .build();
    }

    public SolicitationExcludeResponse toSolicitateExcludeResponse(SolicitationExclude solicitationExclude) {
        return SolicitationExcludeResponse.builder()
                .dateSolicitation(solicitationExclude.getDateSolicitation())
                .cep(solicitationExclude.getAddress().getCep())
                .dateExclude(solicitationExclude.getDateExclude())
                .city(solicitationExclude.getAddress().getCity())
                .cpf(solicitationExclude.getCpfValue())
                .executed(solicitationExclude.getExecuted())
                .neighborhood(solicitationExclude.getAddress().getNeighborhood())
                .state(solicitationExclude.getAddress().getState())
                .name(solicitationExclude.getName())
                .number(solicitationExclude.getAddress().getNumber())
                .street(solicitationExclude.getAddress().getStreet())
                .telephone(solicitationExclude.getTelephone())
                .build();
    }


    public List<SolicitationExcludeResponse> toSolicitateExcludeResponseList(Result<List<SolicitationExclude>> allSolicitationExcluded) {
        return allSolicitationExcluded.getBody().stream().map(this::toSolicitateExcludeResponse).toList();
    }
}
