package com.techchallenge.infrastructure.persistence.mapper;

import com.techchallenge.domain.entity.SolicitationExclude;
import com.techchallenge.domain.valueobject.Address;
import com.techchallenge.infrastructure.persistence.entity.AddressEntity;
import com.techchallenge.infrastructure.persistence.entity.SolicitationExcludeEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SolicitationEntityMapper {
    public SolicitationExcludeEntity toSolicitationExcludeEntity(SolicitationExclude solicitationExclude) {
        return SolicitationExcludeEntity.builder()
                .cpf(solicitationExclude.getCpfValue())
                .dateExclude(solicitationExclude.getDateExclude())
                .dateSolicitation(solicitationExclude.getDateSolicitation())
                .nome(solicitationExclude.getName())
                .address(getEnderecoEntity(solicitationExclude.getAddress()))
                .executed(solicitationExclude.getExecuted())
                .telephone(solicitationExclude.getTelephone())
                .build();
    }

    private AddressEntity getEnderecoEntity(Address endereco) {
        return AddressEntity.builder()
                .cep(endereco.getCep())
                .city(endereco.getCity())
                .state(endereco.getState())
                .neighborhood(endereco.getNeighborhood())
                .number(endereco.getNumber())
                .street(endereco.getStreet())
                .build();
    }

    public SolicitationExclude toSolicitationExclude(SolicitationExcludeEntity save) {
        return SolicitationExclude.SolicitationExcludeBuilder
                .aSolicitationExclude()
                .withDateExclude(save.getDateExclude())
                .withDateSolicitation(save.getDateSolicitation())
                .withCpf(save.getCpf())
                .withEndereco(getEndereco(save.getAddress()))
                .withName(save.getNome())
                .withExecuted(save.getExecuted())
                .withTelephone(save.getTelephone())
                .build();
    }

    private Address getEndereco(AddressEntity endereco) {
        return Address.EnderecoBuilder.anEndereco()
                .withCep(endereco.getCep())
                .withCity(endereco.getCity())
                .withNeighborhood(endereco.getNeighborhood())
                .withNumber(endereco.getNumber())
                .withState(endereco.getState())
                .withStreet(endereco.getStreet())
                .build();
    }

    public List<SolicitationExclude> toSolicitationExcludeList(List<SolicitationExcludeEntity> list) {
        return list.stream().map(this::toSolicitationExclude).toList();
    }
}
