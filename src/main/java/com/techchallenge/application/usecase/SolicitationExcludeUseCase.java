package com.techchallenge.application.usecase;

import com.techchallenge.core.response.Result;
import com.techchallenge.domain.entity.SolicitationExclude;

import java.util.List;

public interface SolicitationExcludeUseCase {

    SolicitationExclude insert(SolicitationExclude solicitationExclude);

    SolicitationExclude findByCpf(String cpf);
    Result<List<SolicitationExclude>> findAllSolicitationExcluded(int page, int size);
    Result<List<SolicitationExclude>> findAllSolicitationNotExcluded(int page, int size);
}
