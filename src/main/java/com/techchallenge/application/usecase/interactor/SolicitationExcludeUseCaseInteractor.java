package com.techchallenge.application.usecase.interactor;

import com.techchallenge.application.gateway.SolicitationExcludeGateway;
import com.techchallenge.application.usecase.SolicitationExcludeUseCase;
import com.techchallenge.core.exceptions.BusinessException;
import com.techchallenge.core.exceptions.NotFoundException;
import com.techchallenge.core.response.Result;
import com.techchallenge.domain.entity.SolicitationExclude;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolicitationExcludeUseCaseInteractor implements SolicitationExcludeUseCase {

    private SolicitationExcludeGateway gateway;

    public SolicitationExcludeUseCaseInteractor(SolicitationExcludeGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public SolicitationExclude insert(SolicitationExclude solicitationExclude) {
        if(gateway.exists(solicitationExclude.getCpfValue())){
            throw new BusinessException("Solicitation has already been registered");
        }
        return gateway.insert(solicitationExclude);
    }

    @Override
    public SolicitationExclude findByCpf(String cpf) {
        return gateway.findByCpf(cpf).orElseThrow(() -> new NotFoundException("Solicitation not found"));
    }

    @Override
    public Result<List<SolicitationExclude>> findAllSolicitationExcluded(int page, int size) {
        return gateway.findAllSolicitationExcluded(page,size);
    }

    @Override
    public Result<List<SolicitationExclude>> findAllSolicitationNotExcluded(int page, int size) {
        return gateway.findAllSolicitationNotExcluded(page,size);
    }
}
