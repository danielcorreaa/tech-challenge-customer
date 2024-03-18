package com.techchallenge.application.gateway;

import com.techchallenge.core.response.Result;
import com.techchallenge.domain.entity.SolicitationExclude;
import org.apache.coyote.Response;

import java.util.List;
import java.util.Optional;

public interface SolicitationExcludeGateway {

    SolicitationExclude insert(SolicitationExclude solicitationExclude);

    Optional<SolicitationExclude> findByCpf(String cpf);

    Boolean exists(String cpf);
    Result<List<SolicitationExclude>> findAllSolicitationExcluded(int page, int size);
    Result<List<SolicitationExclude>> findAllSolicitationNotExcluded(int page, int size);

}
